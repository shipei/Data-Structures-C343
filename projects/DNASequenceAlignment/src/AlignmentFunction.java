public class AlignmentFunction {

    static Result[][] align(final String s1, final String s2, StringBuilder s1_aligned, StringBuilder s2_aligned) {
    	// put your solution here
    	//initialize a matrix:
    	Result[][] matrix = new Result[s1.length()+1][s2.length()+1];
    	for(int m = 0; m < matrix.length; m++) {
    		for(int n = 0; n < matrix[0].length; n++) {
    			matrix[m][n] = new Result(0, null);
    		}
    	}
    	//first row are all insert:
		int val = -1;
		for(int row1 = 1; row1 < matrix[0].length; row1++) {
			matrix[0][row1].score = val;
			matrix[0][row1].dir = Mutation.I;
			val--;
		}
		//first column are all delete:
		int val2 = -1;
		for(int col1 = 1; col1 < matrix.length; col1++) {
			matrix[col1][0].score = val2;
			matrix[col1][0].dir = Mutation.D;
			val2--;
		}
    	//initialize upper left corner:
    	matrix[0][0].dir = Mutation.M;
    	matrix[0][0].score = 0;
    	//for every elements in matrix:
    	for(int i = 1; i <= s1.length(); i++) {
    		for(int j = 1; j <= s2.length(); j++) {
    			//upper left (match): if match:
    			Result match = new Result(0, null);	
    			if(s1.charAt(i-1) == s2.charAt(j-1)) {
    				match = new Result(matrix[i-1][j-1].score+2, Mutation.M);
    			}
    			else {
    				match = new Result(matrix[i-1][j-1].score-2, Mutation.M);
    			}
    			//top element (insert):
    			Result insert = new Result(matrix[i-1][j].score-1, Mutation.I);
    			//bottom element (delete):
    			Result delete = new Result(matrix[i][j-1].score-1, Mutation.D);
    			//compare and find the max score from 3 directions:
    			if(Math.max(Math.max(match.score, insert.score), delete.score) == match.score) {
    				matrix[i][j] = match;
    			}
    			else if(Math.max(Math.max(match.score, insert.score), delete.score) == insert.score) {
    				matrix[i][j] = insert;
    			}
    			else {
    				matrix[i][j] = delete;
    			}
    		}
    	}
    	//trace back to start point:
    	int backtrack_row = s1.length();
    	int backtrack_column = s2.length();
    	int t = s1.length()-1;
		int s = s2.length()-1;
    	//1.start from bottom left:
    	Result result = matrix[s1.length()][s2.length()];
    	while(result != matrix[0][0]) {	
    		if(result.dir == Mutation.M) {
    			//append values on both strings:
    			s1_aligned.append(s1.charAt(t));
    			s2_aligned.append(s2.charAt(s));
    			//then go to upper left:
    			result = matrix[--backtrack_row][--backtrack_column];
    			--t;
    			--s;
    		}
    		else if(result.dir == Mutation.I) {
    			//append char to s2 and " " to s1:
    			s2_aligned.append(s2.charAt(s));
    			s1_aligned.append("_");
    			//then go up:
    			result = matrix[backtrack_row][--backtrack_column];
    			--s;
    		}
    		else {
    			//append char to s1 and " " to s2:
    			s1_aligned.append(s1.charAt(t));
    			s2_aligned.append("_");
    			//then go left:
    			result = matrix[--backtrack_row][backtrack_column];
    			--t;
    		}
    	}	
    	return matrix;	
    }
}