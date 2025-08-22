package org.assessment.filesystem;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        FileSystem fs = new FileSystem();
        System.out.println(" ==JAVA FILE SYSTEM==");
        System.out.println("Available commands:");
        System.out.println("  mkdir name - Create directory");
        System.out.println("  cd name - Change directory");
        System.out.println("  touch name - Create file");
        System.out.println("  ls - List files");
        System.out.println("  pwd - Show current path");
        System.out.println("  rm name - Remove file/directory");
        System.out.println("  exit ");
        System.out.println();

        fs.pwd();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(") ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String arg = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "mkdir":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: mkdir name");
                    } else {
                        fs.mkdir(arg);
                    }
                    break;
                case "cd":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: cd name");
                    } else {
                        fs.cd(arg);
                    }
                    break;
                case "touch":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: touch name");
                    } else {
                        fs.touch(arg);
                    }
                    break;
                case "ls":
                    fs.ls();
                    break;
                case "pwd":
                    fs.pwd();
                    break;
                case "rm":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: rm name");
                    } else {
                        fs.rm(arg);
                    }
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }

        System.out.println("Goodbye!");
        scanner.close();

    }

}