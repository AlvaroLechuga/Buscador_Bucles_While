package dam.bucles.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Funciones {

    List<File> lista = new ArrayList<>();

    List<String> lineas = new ArrayList<>();

    int contador = 0;
    int primera;
    String lineaTemporal;
    int cierre;
    int apertura;

    public List<File> ObtenerFicheros(File entrada) {
        if (!entrada.exists()) {
            System.out.println(entrada.getName() + " no encontrado.");
        } else if (entrada.isFile()) {
            if (getFileExtension(entrada).equals("java")) {
                lista.add(entrada.getAbsoluteFile());
            }
        } else if (entrada.isDirectory()) {
            File[] files = entrada.listFiles();
            if (files.length > 0) {
                for (File f : files) {
                    ObtenerFicheros(f);
                }
            }
        }
        return lista;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public boolean Guardar(List<File> origen, File destinoFicheros) {

        try {

            File bucles = new File(destinoFicheros.getAbsolutePath() + "/Bucles");

            if (!bucles.exists()) {
                bucles.mkdir();
            }

            FileReader fr;
            BufferedReader bf;

            FileWriter escritura = null;
            BufferedWriter bw = null;

            try {
                escritura = new FileWriter(bucles.getAbsolutePath() + "/bucle.txt");
                bw = new BufferedWriter(escritura);
            } catch (IOException ex) {
                Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (File file : origen) {
                try {
                    try {
                        fr = new FileReader(file);
                        bf = new BufferedReader(fr);

                        String sCadena;

                        while ((sCadena = bf.readLine()) != null) {

                            if (sCadena.contains("while")) {
                                lineaTemporal = sCadena;

                                primera = lineaTemporal.indexOf("}");

                                if (primera == -1) {
                                    contador = 1;
                                    bw.write(sCadena + "\n");

                                    System.out.println(lineaTemporal);

                                    while ((sCadena = bf.readLine()) != null) {
                                        lineaTemporal = sCadena;
                                        cierre = lineaTemporal.indexOf("}");
                                        apertura = lineaTemporal.indexOf("{");

                                        if (apertura != -1) {
                                            contador++;
                                        }

                                        if (cierre != -1) {
                                            contador--;
                                        }

                                        bw.write(sCadena + "\n");
                                        System.out.println(lineaTemporal);

                                        if (contador == 0) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                    } catch (FileNotFoundException ex) {
                        return false;
                    } catch (IOException ex) {
                        return false;
                    }
                    bf.close();
                    fr.close();
                } catch (IOException ex) {}
            }
            bw.close();
            escritura.close();
            return true;
        } catch (IOException ex) {
            return false;
        }

    }
}
