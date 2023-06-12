package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static void ImageJFrame(String directory)
    {
        System.out.println("Directory: "+directory);
        Queue<String> fileNames = new ArrayDeque<>();

        File[] files = new File(directory).listFiles();
        for (File file: files
             ) {
            if (file.getName().contains("=") || file.getName().contains("(") || file.getName().contains(" ") || file.getName().contains("\"")) {
                System.out.println(directory + file.getName());
            }
//            if (file.getName().startsWith("A_")) {
//                fileNames.add(directory + file.getName());
//            }
        }

        System.out.println("Remaining file count: "+fileNames.size());
        JFrame f = new JFrame("Add an image to JFrame");

        BufferedImage img = null;
        final File[] currentFile = {new File(fileNames.poll())};

        try {
            img = ImageIO.read(currentFile[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(img.getWidth()*3, img.getHeight()*3,
                Image.SCALE_SMOOTH);

        ImageIcon icon = new ImageIcon(dimg);
        JTextField textField = new JTextField(10);

        JLabel label = new JLabel(icon);

        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String code = textField.getText();
                System.out.println("Original: "+ currentFile[0].getName());
                System.out.println("RenamedTo: "+ currentFile[0].getName().replaceAll("A_",code+"_"));
                currentFile[0].renameTo(new File(directory + currentFile[0].getName().replaceAll("A_",code+"_")));
                BufferedImage img = null;
                currentFile[0] = new File(fileNames.poll());

                try {
                    img = ImageIO.read(currentFile[0]);
                } catch (IOException f) {
                    f.printStackTrace();
                }

                Image dimg = img.getScaledInstance(img.getWidth()*3, img.getHeight()*3,
                        Image.SCALE_SMOOTH);

                ImageIcon icon = new ImageIcon(dimg);
                label.setIcon(icon);
                textField.setText("");
            }
        };


        textField.addActionListener( action );

        f.add(textField, BorderLayout.SOUTH);
        f.add(label);
        f.setPreferredSize(new Dimension(1000, 500));
        f.pack();
        f.setVisible(true);

    }
    public static void main(String[] args) {
        //ImageJFrame("F:\\ddddocr\\dddd_trainer\\1234k_Done"+"\\");
        //ImageJFrame(String.valueOf(FileSystems.getDefault().getPath("").toAbsolutePath())+"\\");

        for (int i=0; i<20; i++) {
            UUID uuid = UUID.randomUUID();
            String name = uuid.toString();
            name = name.replaceAll("-","");
            name = name.substring(0,32);
            System.out.println(name);
            System.out.println(i);

            try (BufferedInputStream in = new BufferedInputStream(new URL("https://www.icris.cr.gov.hk/csci/shwcaptcha.do?checkPoint=login&rand=0.9902861793597917").openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("D:\\Users\\Eric\\Downloads\\Captcha\\testing\\" + "A_" + name + ".webp")) {

                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        int[] numbers = {2,7,11,15};
//        int target = 9;
//        System.out.println(Arrays.toString(twoSum(numbers, target)));
        //isValidSudoku(board);
    }


//    public static int[] twoSum(int[] numbers, int target) {
//        HashMap<Integer,Integer> hashMap = new HashMap<>();
//        for (int i=0; i< numbers.length; i++) {
//            int diff = target - numbers[i];
//            if (hashMap.containsKey(diff)) {
//                return new int[]{hashMap.get(diff)+1,i+1};
//            };
//            hashMap.put(numbers[i],i);
//        }
//        return new int[0];
//    }



}
