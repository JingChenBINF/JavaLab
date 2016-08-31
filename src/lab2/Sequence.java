package lab2;

import java.util.Random;

public class Sequence 
{
	public static void main(String[] args)
	{
		Random random = new Random();
		String str[] = {"A","C","G","T"};
		int count = 0; 
		for (int x = 0; x < 1000; x++)
		{
			String seq = "";
			seq = seq + str[random.nextInt(4)];
			seq = seq + str[random.nextInt(4)];
			seq = seq + str[random.nextInt(4)];
		
			System.out.println(seq);
			if (seq.equals("AAA"))
			{
				count++;
			}
		}
		System.out.println("AAA/Total: " + count + "/1000");
	}
}
