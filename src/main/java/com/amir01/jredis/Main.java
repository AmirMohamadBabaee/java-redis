package com.amir01.jredis;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        RedisClient redisClient = RedisClient.getInstance();
        String fileName = "../NYSE_20210301.csv";
        init_redis(redisClient, fileName);
        while(scan.hasNext()) {
            String command = scan.nextLine();
            String[] split_command = splitter(command);
            boolean status = parser(redisClient, split_command);
            if(status) {
                break;
            }
        }
        redisClient.jedisClose();

    }

    public static String[] splitter(String command) {
        return command.split(" ");
    }

    public static boolean parser(RedisClient redisClient, String[] command_array) {

        if(command_array[0].equals("create")) {

            if(command_array.length == 3) {
                boolean output = redisClient.create(command_array[1], command_array[2]);
                System.out.println("[log] " + command_array[0] + " " + command_array[1] + " "
                    + command_array[2]);
                System.out.println(output);
                System.out.println();
                return false;
            }

        } else if(command_array[0].equals("fetch")) {

            if(command_array.length == 2) {
                String output = redisClient.fetch(command_array[1]);
                boolean isExist = redisClient.isExist(command_array[1]);
                System.out.println("[log] " + command_array[0] + " " + command_array[1]);
                System.out.println(isExist);
                if(isExist) {
                    System.out.println(output);
                }
                System.out.println();
                return false;
            }

        } else if(command_array[0].equals("update")) {

            if(command_array.length == 3) {
                boolean output = redisClient.update(command_array[1], command_array[2]);
                System.out.println("[log] " + command_array[0] + " " + command_array[1] + " "
                    + command_array[2]);
                System.out.println(output);
                System.out.println();
                return false;
            }

        } else if(command_array[0].equals("delete")) {

            if(command_array.length == 2) {
                boolean output = redisClient.delete(command_array[1]);
                System.out.println("[log] " + command_array[0] + " " + command_array[1]);
                System.out.println(output);
                System.out.println();
                return false;
            }

        } else if(command_array[0].equals("exit")) {
            System.out.println("[log] Stop JRedis...");
            return true;
        }

        return false;
    }

    public static void init_redis(RedisClient redisClient, String csv_path) {
        try (CSVReader reader = new CSVReader(new FileReader(csv_path))) {

            List<String[]> r = reader.readAll();
            r.forEach(x -> redisClient.getJedisInstance().set(x[0], x[1]));

        } catch (IOException | CsvException ex) {
            ex.printStackTrace();
        }
    }

}
