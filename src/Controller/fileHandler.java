package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class fileHandler
{
    private String filename ;
    private FileWriter writer ;
    private PrintWriter out ;
    private FileReader fp ;
    private BufferedReader br ;

    public fileHandler(String filename)
    {
        this.filename = filename ;
    }

    public void fileWrite(String str)
    {
        try{
            writer = new FileWriter(this.filename, true);
            out = new PrintWriter(writer) ;
            out.println(str);
            out.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public ArrayList<String> readFile() throws FileNotFoundException, IOException
    {
        ArrayList<String> tmp = new ArrayList<String>() ;
        fp = new FileReader(this.filename) ;
        br = new BufferedReader(fp) ;

        String line ;

        while((line = br.readLine()) != null){
            tmp.add(line) ;
        }
        br.close();
        return tmp ;
    }
}
