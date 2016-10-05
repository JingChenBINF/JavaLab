package lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  FastaSequence  
{
	private String mySequence;
	private String sequence;
	public FastaSequence()
	{
		sequence = "";
		mySequence = "";
	}
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{
		String path = filepath;
		List<FastaSequence> sequenceList = new  ArrayList<FastaSequence>();
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(path))))
		{
			String line = null;        	
			List<String> alllines = new ArrayList<String>();			
			int count = 0;
			List<Integer> headerline = new ArrayList<Integer>();
			while ((line = reader.readLine()) != null)
			{
				alllines.add(line);
				++ count;
				if (line.startsWith(">"))
				{
					headerline.add(count);			
				}
			}
			for (int x=0; x< headerline.size(); ++x )
			{
				if (x < headerline.size()-1)
				{
					String Total = "";
					for (int z = headerline.get(x)-1; z < headerline.get(x+1)-1; ++z)
					{
						Total = Total + alllines.get(z) + "\n";					
					}
					FastaSequence tmp_seq = new FastaSequence();
					tmp_seq.mySequence= Total;
					sequenceList.add(tmp_seq);
				}
				else
				{
					String Total = "";
					for (int z = headerline.get(x)-1; z < alllines.size(); ++z)
					{
						Total = Total + alllines.get(z) + "\n";	
					}
					FastaSequence tmp_seq = new FastaSequence();
					tmp_seq.mySequence = Total;
					sequenceList.add(tmp_seq);        	
				}
			}
		reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return sequenceList;
	}	
	public String getHeader()
	{		
		String header1 = mySequence.split("\n")[0];
		String [] header2 = header1.split(">");
		return header2[1];		
	}
	public String getSequence() 
	{	
		String[] Seq = mySequence.split("\n");
		String out = "";
		for (int x= 1; x< Seq.length; x++)
		{			 
			sequence = sequence + Seq[x];
			out = out + Seq[x] + "\n";
		}
		return out;
	}
	public float getGCRatio()
	{
		int countgc = 0;
		for (int x = 0; x < sequence.length(); x++)
		{
			String[] base = sequence.split("");	
			if (base[x].equals("C") || base[x].equals("G"))
			{
				countgc ++;
			}
		}
		float gcratio = (float) countgc/(float) sequence.length();
		return gcratio;
	}	
	public static void writeUnique(File inFile, File outFile) throws Exception
	{
		String path = inFile.toString();
		List<FastaSequence> uniqSequence = FastaSequence.readFastaFile(path);
		List<String> sequence = new ArrayList<String>();
		Map <String, Integer> map = new HashMap<String,Integer>();
		FileOutputStream fos = new FileOutputStream(outFile);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));		
		for (FastaSequence fs: uniqSequence)
		{		
			sequence.add(fs.getSequence());	
		}
		for (int x = 0; x< sequence.size();x++)
		{
			Integer count = map.get(sequence.get(x));
			if (count == null)
			{
				count = 0;
			}
			count ++;
			map.put(sequence.get(x), count);
		}
		List<Integer> value= new ArrayList<Integer>();
		value.addAll(map.values());
		Collections.sort(value);
		List<String> key= new ArrayList<String>();
		key.addAll(map.keySet());
		for(int x = 0; x < value.size(); x++)
		{
			for(int y = 0; y< key.size(); y++)
			{
				if (map.get(key.get(y)) == value.get(x))
				{
					System.out.println(">" + value.get(x) + "\n" + key.get(y));
					bw.write(">" + value.get(x) + "\n" + key.get(y));
				}
			}		
		}
		bw.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		//List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/chenjing/Downloads/FastaFile.txt");
		//for( FastaSequence fs : fastaList)
		//{
		//	System.out.println(fs.getHeader());
		//	System.out.println(fs.getSequence());
		//	System.out.println(fs.getGCRatio());
		//}
		File inFile = new File ("/Users/chenjing/Downloads/FastaFile.txt");
		File outFile = new File ("/Users/chenjing/Downloads/Out.txt");
		FastaSequence.writeUnique(inFile,outFile);
	}
}
