package cn.kunsland;

public class Word {

	public static boolean validateWord(String word) {
		if (word.length() > 32)
			return false; // assume the longest word is
							// perhydrocyclopentanophenanthrene
		String regex = "[a-zA-Z]+(\\s?|-?|\\.?)[a-zA-Z]*[\\.,:;]*";
		return word.matches(regex);
	}

	public static String regularWord(String word) {
		word = word.replaceAll("[\\.,:;]*$", "");
		return word;
	}

	public static void main(String[] args) {
		System.out.println(Word.validateWord("hello"));
		System.out.println(Word.validateWord("helloHello"));
		System.out.println(Word.validateWord("zA"));
		System.out.println(Word.validateWord("hello hello"));
		System.out.println(Word.validateWord("hello,"));
		System.out.println(Word.validateWord("hello;"));
		System.out.println(Word.validateWord("hello."));
		System.out.println(Word.validateWord("hello:"));
		System.out.println(Word.validateWord("hello-hello"));
		System.out.println(Word.validateWord("et al."));

		System.out.println(Word.validateWord("hello12"));
		System.out.println(Word.validateWord("hello£»"));
		System.out.println(Word.validateWord("hello£º"));
		System.out.println(Word.validateWord(" hello"));
		System.out.println(Word.validateWord("hello.."));
		System.out.println(Word
				.validateWord("hellhellhellhellhellhellhellhe0"));
		

		System.out.println(Word.validateWord("hello.hello;"));
		System.out.println(Word.regularWord("hello.hello;"));
		System.out.println(Word.regularWord("hello.hello.."));
	}
}
