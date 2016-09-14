package lab3;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class Quiz 
{
	public static Map<String, String> hashmap_building(String[] SHORT_NAMES,String[] FULL_NAMES)
	{
		Map<String, String> dictionary = new HashMap<String, String>();

		for (int index = 0; index < 20; ++index)
		{
			dictionary.put(SHORT_NAMES[index], FULL_NAMES[index]);
		}
		return dictionary;
	}
	public static void match(String[] SHORT_NAMES,String[] FULL_NAMES,Map<String, String> AA_dictionary)
	{
		int count_right = 0;
		Random random = new Random();
		long start = System.currentTimeMillis();
		while (true)
		{
			int index = random.nextInt(20);
			String question = FULL_NAMES[index];
			System.out.println(question);
			
			String aString = System.console().readLine().toUpperCase();
			if (question == AA_dictionary.get(aString))
			{
				++count_right;
				long current_time = System.currentTimeMillis();
				String aChar = "" + aString.charAt(0);
				System.out.println( aChar);
				System.out.println("right." + "  " + "Score=" + count_right + " ; " + "second = " + (current_time-start)/1000.0 + " out of 30" );
				System.out.println(" ");
			}
			else
			{				
				String right_answer = SHORT_NAMES[index];
				System.out.println("WRONG should be " + right_answer);
				System.out.println("Test ends with score of " + count_right);
				System.exit(0);
			}
		}
	}

	public static void main(String[] args)

	{
		String[] SHORT_NAMES = 
			{ "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" };
		String[] FULL_NAMES = 
			{
			"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine",
			"glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine",
			"leucine",  "lysine", "methionine", 
			"phenylalanine", "proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"};
		Map<String, String> AA_dictionary = hashmap_building(SHORT_NAMES, FULL_NAMES);
		Timer timer = new Timer();
		timer.schedule(
				new java.util.TimerTask()
				{
					public void run()
					{
						System.exit(0);
					}
				},
				30*1000	
		);
		match(SHORT_NAMES, FULL_NAMES,AA_dictionary);
	}

}
