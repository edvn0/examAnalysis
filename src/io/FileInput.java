package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileInput
{
    private Scanner scanner;

    FileInput(String dir) throws FileNotFoundException
    {
        scanner = new Scanner(new File(dir));
    }

    List<String[]> fileInput()
    {
        List<String[]> values = new ArrayList<>();
        while (scanner.hasNext())
        {
            values.add(scanner.nextLine().split(","));
        }
        return values;
    }
}
