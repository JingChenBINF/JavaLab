package lab2;

import java.util.Random;

public class Sequence2 
{
	public static void main(String[] args)
	{
		Random random = new Random();
		int count = 0; 
		for (int x = 0; x < 1000; x++)
		{
			String seq = "";
			for (int y = 0; y < 3; y++)
			{
				float f = random.nextFloat();
				if (f <= 0.12)
					seq = seq + "A";
				else if (f > 0.12 && f <= 0.5)
					seq = seq + "C";
				else if (f > 0.5 && f <= 0.89)
					seq = seq + "G";
				else if (f > 0.89 && f <= 1.00)
					seq = seq + "T";
			}
			System.out.println(seq);
			if (seq.equals("AAA"))
			{
				count++;
			}
		}
		System.out.println("AAA/Total: "+ count + "/1000");
	}
}
