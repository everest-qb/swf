import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RecuresT {

	@Test
	public void test() {
		List<Integer> r=recurse(10);
		for(int i:r){
			System.out.println(i);
		}
		
		assertTrue(r.size()==10);
	}

	
	public List<Integer> recurse(int code){
		if(code>0){
			int inCode=code-1;
			List<Integer> retrunValue= recurse(inCode);
			retrunValue.add(code);
			return retrunValue;
			
		}else{
			List<Integer> retrunValue=new ArrayList<>();
			retrunValue.add(code);
			return retrunValue;
		}
		
	}
	
}
