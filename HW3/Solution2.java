import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution2 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output2.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {
	static final int MAX_N = 20000;
	static final int MAX_E = 80000;

	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int Answer;
	static int[] anc = new int[MAX_N+1]; //ancestor

	public static void MakeSet()
	{
		for(int i=1; i<=N; i++) anc[i] = i;
	}

	public static int FindSet(int x)
	{
		if(x != anc[x]) anc[x] = FindSet(anc[x]);
		return anc[x];
	}

	public static void Union(int x, int y)
	{
		anc[FindSet(y)] = FindSet(x);
	}

	public static class edge
	{
		int u;
		int v;
		int w;

		public edge(int u, int v, int w)
		{
			this.u = u;
			this.v = v;
			this.w = w;
		}
	}

	public static class myHeap
	{
		edge[] heap;
		int len;

		public myHeap(int MAX_N)
		{
			heap = new edge[MAX_N + 1];
			len = 0;
		}

		private int parent(int pos)
		{
			return pos/2;
		}

		private boolean isLeaf(int pos)
		{
			if (pos >= (len/2) && pos<=len) return true;
			return false;
		}

		private int leftChild(int pos)
		{
			return (pos*2);
		}

		private int rightChild(int pos)
		{
			return (pos*2)+1;
		}

		private void swap(int left, int right)
		{
			edge tmp;
			tmp = heap[left];
			heap[left] = heap[right];
			heap[right] = tmp;
		}

		public void insert(edge e)
		{
			heap[++len] = e;
			int cur = len;

			while (cur>1 && heap[cur].w > heap[cur/2].w)
			{
				swap(cur, parent(cur));
				cur = cur/2;
			}
		}

		public edge delete()
		{
			edge del = heap[1];
			heap[1] = heap[len--];
			heapify(1, len);
			return del;
		}

		public void heapify(int k, int size)
		{
			int max = k;
			if((size >= leftChild(k)) && heap[leftChild(k)].w > heap[max].w) max = leftChild(k);
			if((size >= rightChild(k)) && heap[rightChild(k)].w > heap[max].w) max = rightChild(k);
			if(max != k)
			{
				swap(k, max);
				heapify(max, size);
			}
		}
	}

	public static int MST()
	{
		int max=0, j=0;
		myHeap H = new myHeap(E);

		for(int i=0; i<E; i++) H.insert(new edge(U[i], V[i], W[i]));
		MakeSet();

		while(j<N-1)
		{
			edge e = H.delete();
			if(FindSet(e.u) != FindSet(e.v))
			{
				max += e.w;
				anc[FindSet(e.v)] = FindSet(e.u);
				Union(e.u, e.v);
				j++;
			}
		}

		return max;
	}

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 양 끝점의 번호를 U[i], V[i]에 읽어들이고, i번째 간선의 가중치를 W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = MST();
			//I implemented MST() with kruskal algorithm, so time complexity is O(ElogN).

			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
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

