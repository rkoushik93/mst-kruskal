package kruskal;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Kruskal {
    int vertices,maxedge,count=0,mstcount=0,numcount=0;
    int[][] adj;
    int[] weight,output;
    boolean[] visited;
    int[][] mstadj;
    int[] stack;
    int top=-1;
    
    boolean isEmpty(){
        if(top==-1)
            return true;
        else
            return false;
    }
    
    void init(){
        mstadj=new int[vertices-1][3];
        output=new int[vertices];
        for(int i=0;i<vertices;i++)
            output[i]=-1;
    }
    
    void push(int x){
        stack[++top]=x;
    }
    
    int pop(){
        int popElement=stack[top];
        --top;
        return popElement;
    }
    
    int pathExists(int src, int dest){
        dfs(src);
        for(int i=0;i<numcount;i++){
            if(output[i]==dest)
                return 1;
        }
        return 0;
    }
    
    void dfs(int src){
        visited=new boolean[vertices];
        int currentelement;
        for(int i=0;i<vertices;i++)
            visited[i]=false;
        numcount=0;
        stack=new int[vertices];
        stack[++top]=src;
        while(!isEmpty()){
            currentelement=pop();
            visited[currentelement]=true;
            output[numcount]=currentelement;
            //System.out.println(currentelement);
            for(int i=0;i<mstcount;i++){
                if(mstadj[i][0]==currentelement && visited[mstadj[i][1]]==false){
                    push(mstadj[i][1]);
                    //df.visited[i]=true;
                }
                    
            }
            numcount++;
            if(numcount>=vertices)
                break;
        }
    }
    public static void main(String[] args) {
        Scanner kbd=new Scanner(System.in);
        Kruskal pr=new Kruskal();
        int temp1,temp2,temp3,tempmin=0,c,currentVertex=0,destVertex=0,tempcount=0,row=0,column=0,tempIndex=0,tempVar=0;
        FileReader inputStream = null;
        FileWriter outputStream = null;
        BufferedWriter bw=null;
        int[][] adjTemp;
        int[][] outputArray;
        int tempItr=0;
        char check='a';
        boolean flag=true;
        //int[][] outputArray;
        pr.count=0;
        
        System.out.print("Enter the number of vertices : ");
        pr.vertices=kbd.nextInt();
        pr.init();
        
        pr.maxedge=pr.vertices*(pr.vertices-1);
        pr.adj=new int[pr.vertices][pr.vertices];
        adjTemp=new int[pr.maxedge][3];
        //outputArray=new int[pr.vertices-1][3];
        
        
        try{
            inputStream = new FileReader("graph2.txt");
            while ((c = inputStream.read()) != -1) {
                
                while(flag==true && c!=-1){
                    if(tempcount==0){
                        currentVertex=c-'0';
                        //System.out.println("Iteration"+pr.count+", currentVertex value : "+currentVertex);
                        adjTemp[pr.count][0]=currentVertex;
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        destVertex=c-'0';
                        adjTemp[pr.count][1]=destVertex;
                        //System.out.println("Iteration"+pr.count+", desVertex value : "+destVertex);
                        c = inputStream.read();
                        c = inputStream.read();
                        adjTemp[pr.count][2]=c-'0';
                        //pr.adj[currentVertex][destVertex]=c-'0';
                        //pr.adj[destVertex][currentVertex]=c-'0';
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        check=(char)c;
                        pr.count++;
                    }

                    if(check=='('){
                        //c = inputStream.read();
                        c = inputStream.read();
                        destVertex=c-'0';
                        adjTemp[pr.count][0]=currentVertex;
                        adjTemp[pr.count][1]=destVertex;
                        //System.out.println("Iteration"+pr.count+" currentVertex value : "+currentVertex);
                        //System.out.println("Iteration"+pr.count+" destVertex value : "+destVertex);
                        c = inputStream.read();
                        c = inputStream.read();
                        adjTemp[pr.count][2]=c-'0';
                        //pr.adj[currentVertex][destVertex]=c-'0';
                        //pr.adj[destVertex][currentVertex]=c-'0';
                        c = inputStream.read();
                        c = inputStream.read();
                        c = inputStream.read();
                        check=(char)c;
                        pr.count++;
                        tempcount++;
                    }

                    if(check==','){
                        flag=false;
                    }
                    
                    
                }
                tempcount=0;
                c = inputStream.read();
                c=inputStream.read();
                flag=true;
            }
        }
        catch(IOException e){
            System.out.println("Some error");
        }
        
        for(int i=0;i<pr.count;i++){
            for(int j=i+1;j<pr.count;j++){
                if(adjTemp[i][2]>adjTemp[j][2]){
                    tempVar=adjTemp[i][0];
                    adjTemp[i][0]=adjTemp[j][0];
                    adjTemp[j][0]=tempVar;
                    
                    tempVar=adjTemp[i][1];
                    adjTemp[i][1]=adjTemp[j][1];
                    adjTemp[j][1]=tempVar;
                    
                    tempVar=adjTemp[i][2];
                    adjTemp[i][2]=adjTemp[j][2];
                    adjTemp[j][2]=tempVar;
                }
            }
        }
        
        pr.mstadj[0][0]=adjTemp[0][0];
        pr.mstadj[0][1]=adjTemp[0][1];
        pr.mstadj[0][2]=adjTemp[0][2];
        for(int i=0;i<pr.count;i++){
            if(pr.mstcount==pr.vertices-1)
                break;
            if(((pr.pathExists(adjTemp[i][0],adjTemp[i][1]))==0) && (pr.pathExists(adjTemp[i][1],adjTemp[i][0]))==0){
                pr.mstadj[pr.mstcount][0]=adjTemp[i][0];
                pr.mstadj[pr.mstcount][1]=adjTemp[i][1];
                pr.mstadj[pr.mstcount][2]=adjTemp[i][2];
                pr.mstcount++;
            }
        }
        
        System.out.println("\nEdges in the MST :\n");
        System.out.println("From    To   Weight");
        for(int i=0;i<pr.mstcount;i++){
            System.out.println(pr.mstadj[i][0]+"        "+pr.mstadj[i][1]+"      "+pr.mstadj[i][2]);
        }
        
        System.out.println("\nOutput text file has been created");
        outputArray=new int[pr.mstcount*2][3];
        for(int x=0;x<pr.mstcount;x++){
            outputArray[tempItr][0]=pr.mstadj[x][0];
            outputArray[tempItr][1]=pr.mstadj[x][1];
            outputArray[tempItr][2]=pr.mstadj[x][2];
            ++tempItr;
            outputArray[tempItr][0]=pr.mstadj[x][1];
            outputArray[tempItr][1]=pr.mstadj[x][0];
            outputArray[tempItr][2]=pr.mstadj[x][2];
            ++tempItr;
        }
        
        for(int i=0;i<tempItr;i++){
            for(int j=i+1;j<tempItr;j++){
                if(outputArray[i][0]>outputArray[j][0]){
                    tempVar=outputArray[i][0];
                    outputArray[i][0]=outputArray[j][0];
                    outputArray[j][0]=tempVar;
                    
                    tempVar=outputArray[i][1];
                    outputArray[i][1]=outputArray[j][1];
                    outputArray[j][1]=tempVar;
                    
                    tempVar=outputArray[i][2];
                    outputArray[i][2]=outputArray[j][2];
                    outputArray[j][2]=tempVar;
                    
                }
            }
        }
        
        try{
            //outputStream=new FileWriter("outputMST.txt",true);
            PrintWriter writer=new PrintWriter("outputMST.txt","UTF-8");
            temp1=outputArray[0][0];
            temp2=outputArray[0][1];
            temp3=outputArray[0][2];
            writer.print(temp1+"(("+temp2+","+temp3+")");
            for(int itr=1;itr<tempItr;itr++){
                if(temp1==outputArray[itr][0]){
                    writer.print(",("+outputArray[itr][1]+","+outputArray[itr][2]+")");
                }
                else{
                    temp1=outputArray[itr][0];
                    writer.print("),");
                    writer.println();
                    writer.print(temp1+"(("+outputArray[itr][1]+","+outputArray[itr][2]+")");
                }
                
                if(itr==tempItr-1)
                    writer.print(")");
            }
            writer.close();
        }
        
        catch(IOException e){
            
        }
        finally{
            try{
                if(inputStream!=null) inputStream.close();
                if(outputStream!=null) outputStream.close();
                //if(bw!=null) bw.close();
            }
            catch(IOException ex){
            }
        }
    }
    
}
