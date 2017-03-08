
public class ExamPrep {
	int i;
	static int x = 1;
	String str;
	
	public static int theInt(){
	x++;
	return(x);
	
	}
	public static String theInt(String str,int other){
	
	return (str+other);
	}
	
	class nested{
		public void innerClass(){
			System.out.println("You made it to the inner class");
			}
		}
	
	public static void main(String[] args){
		ExamPrep c = new ExamPrep();
		int x = 1;
		String s = "Yo";
		ExamPrep w = new secondExtension();
		ExtendsExamPrep n = new ExtendsExamPrep();
		System.out.println(n.theInt());
		System.out.println(theInt(s,x));
		System.out.println(n.trythis());
		secondExtension sec = new secondExtension();
		System.out.println(sec.trythis());
		ExamPrep.nested p =  w.new nested();
		p.innerClass();
	}
}
