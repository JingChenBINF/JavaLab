package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomProteinGenerator
{
	private ArrayList<Float> prob;
	private ArrayList<Float> frequence;
	private ArrayList<String> aas;

	public RandomProteinGenerator(boolean useUniformFrequencies)
	{	
		boolean uniform = useUniformFrequencies;
		ArrayList<String> aas = new ArrayList<String>(Arrays.asList("A", "C", "D", "E", "F", "G", "H", "I", "K", "L", 
				"M", "N", "P", "Q", "R", "S", "T", "V", "W", "Y" ));
		ArrayList<Float> frequence = new ArrayList<Float>(aas.size());
		ArrayList<Float> prob = new ArrayList<Float>(frequence.size()+1);
		prob.add((float) 0);
		for(int i=1,n=aas.size()+1; i<n; ++i)
		{
			if (uniform == true)
			{
				for(int x=0; x< aas.size(); ++x)
				{
					frequence.add((float)(1.0/aas.size()));
				}
				prob.add(prob.get(i-1)+(float)(1.0/aas.size()));
			}
			else if (uniform == false)
			{
				frequence.addAll(Arrays.asList(0.072658f, 0.024692f, 0.050007f, 0.061087f,
				        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
				        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
				        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
				        0.032955f));
				prob.add(prob.get(i-1)+frequence.get(i-1));
			}
		}
		this.prob = prob;
		this.frequence = frequence;
		this.aas = aas;
	}

	public String getRandomProtein(int length)
	{
		Random random = new Random();		
		String sequence = "";
		for(int x=0; x< length; ++x)
		{
			float f = random.nextFloat();
			for(int y=1, n=aas.size()+1; y<n; ++y)
			{
				if(f> prob.get(y-1) && f < prob.get(y))
				{
					sequence = sequence + aas.get(y-1);
				}
			}
		}
		
		return sequence;
	}
	
	public double getExpectedFrequency(String protSeq)
	{
		String seq = protSeq;
		int length = seq.length();
		double expectedfrequency = 1;
		for(int x=0; x<length; ++x)
		{
			String atom = ""+seq.charAt(x);
			int index = aas.indexOf(atom);
			double probability = frequence.get(index);
			expectedfrequency *= probability;
		}
		
		return expectedfrequency;
	}
	
	public double getFrequencyFromSimulation( String protSeq, int numIterations )
	{
		String seq = protSeq;
		int length = seq.length();
		double frequencysimulation = 1;
		int numbers = numIterations;
		int count = 0;
		for(int x=0; x< numbers; ++x)
		{
			String randomseq =getRandomProtein(length);
			if (randomseq.equals(seq))
			{
				++count;
			}		
		}	
		frequencysimulation	= (double)count/(double)numIterations;
		return frequencysimulation;
	}
	
	public static void main(String[] args) throws Exception
	{
		RandomProteinGenerator uniformGen = new RandomProteinGenerator(true);
		String testProtein = "ACD";
		int numIterations =  10000000;
		System.out.println(uniformGen.getExpectedFrequency(testProtein)); 
		System.out.println(uniformGen.getFrequencyFromSimulation(testProtein,numIterations)); 
		RandomProteinGenerator realisticGen = new RandomProteinGenerator(false);
		System.out.println(realisticGen.getExpectedFrequency(testProtein));  
		System.out.println(realisticGen.getFrequencyFromSimulation(testProtein,numIterations));
		
	}
}
