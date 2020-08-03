import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {

    /*
        ** 주의사항
        정답의 숫자가 매우 크기 때문에 답안은 반드시 int 대신 long 타입을 사용합니다.
        그렇지 않으면 overflow가 발생해서 0점 처리가 됩니다.
        Answer[0]을 a의 개수, Answer[1]을 b의 개수, Answer[2]를 c의 개수라고 가정했습니다.
    */
    static int n;                           // 문자열 길이
    static String s;                        // 문자열
    static long[] Answer = new long[3];     // 정답

    public static void num()
    {
        long[][] a = new long[n+1][n+1];
        long[][] b = new long[n+1][n+1];
        long[][] c = new long[n+1][n+1];

        for(int i=1; i<=n; i++) //1 to n for loop이므로 수행시간은 theta(n)
        {
            //내부는 단순 연산이므로 상수 시간이 소요된다.
            if(s.charAt(i-1) == 'a') a[i][i] = 1;
            else a[i][i] = 0;
            if(s.charAt(i-1) == 'b') b[i][i] = 1;
            else b[i][i] = 0;
            if(s.charAt(i-1) == 'c') c[i][i] = 1;
            else c[i][i] = 0;
        }

        //3중 for loop를 돌고 있으므로 총 수행 시간은 theta(n^3)이다.
        for(int r=1; r<=n; r++) //1 to n for loop
        {
            for(int i=1; i<=n-r; i++) //1 to n-r for loop
            {
                int j = i + r;
                for(int k=i; k<=j-1; k++) //i to i+r-1 for loop
                {
                    //내부는 단순 연산이므로 상수 시간이 소요된다.
                    a[i][j] += a[i][k] * c[k + 1][j] + b[i][k] * c[k + 1][j] + c[i][k] * a[k + 1][j];
                    b[i][j] += a[i][k] * a[k + 1][j] + a[i][k] * b[k + 1][j] + b[i][k] * b[k + 1][j];
                    c[i][j] += b[i][k] * a[k + 1][j] + c[i][k] * b[k + 1][j] + c[i][k] * c[k + 1][j];
                }
            }
        }


        Answer[0] = a[1][n];
        Answer[1] = b[1][n];
        Answer[2] = c[1][n];


        /*
        for(int i=0; i<=n; i++)
        {
            for(int j=0; j<=n; j++)
            {
                    System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
        for(int i=0; i<=n; i++) {
            for (int j = 0; j <=n; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
        for(int i=0; i<=n; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }*/
    }




    public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output4.txt 로 정답을 출력합니다.
		 */
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
        StringTokenizer stk;
        PrintWriter pw = new PrintWriter("output2.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
        for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 파일에서 읽어옵니다.
               첫 번째 행에 쓰여진 문자열의 길이를 n에 읽어들입니다.
               그 다음 행에 쓰여진 문자열을 s에 한번에 읽어들입니다.
			 */
            stk = new StringTokenizer(br.readLine());
            n = Integer.parseInt(stk.nextToken());
            s = br.readLine();

            /////////////////////////////////////////////////////////////////////////////////////////////
            /*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   정답을 구하기 위해 주어진 문자열 s를 여러분이 원하시는 대로 가공하셔도 좋습니다.
			   문제의 답을 계산하여 그 값을 Answer(long 타입!!)에 저장하는 것을 가정하였습니다.
			 */

            /////////////////////////////////////////////////////////////////////////////////////////////
            num();
            /*
            Answer[0] = 0;  // a 의 갯수
            Answer[1] = 0;  // b 의 갯수
            Answer[2] = 0;  // c 의 갯수
            */

            // output4.txt로 답안을 출력합니다.
            pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);
			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
            pw.flush();
        }

        br.close();
        pw.close();
    }
}