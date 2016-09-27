package lab5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
	public static void main(String[] args) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/chenjing/Downloads/FastaFile.txt");
		for( FastaSequence fs : fastaList)
		{
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGCRatio());
		}
	}
}
