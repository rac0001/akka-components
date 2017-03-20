package com.rakesh.component.akka.fileimport;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mac on 3/18/17.
 */
public class CreateCsvExample {


    public static void main(String args[]) throws IOException {

        File f = new File("/codeP/components/src/main/resources/marks.csv");

        List<Integer> intList = IntStream.rangeClosed(1,100000).boxed().collect(Collectors.toList());

        List<String> finalList  =intList.stream().map(in -> in+","+"John Doe"+","+(int)( Math.random() * 5 + 1 )+","+ "A"
        ).collect(Collectors.toList());

        FileUtils.writeLines(f,finalList);

    }


}
