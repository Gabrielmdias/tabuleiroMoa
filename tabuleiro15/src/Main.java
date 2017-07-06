/**
 * @author gabriel Martinelli Dias
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Main {
    public static void main(String args[]) {

        HashMap<Integer, No> abertos = new HashMap<>();
        
        PriorityQueue<No> fechados = new PriorityQueue<>();
        
        Scanner scn = new Scanner(System.in);
        
        List<Integer> matRaiz = new ArrayList<>();
        
        for (int i = 0; i < 16; i++) matRaiz.add(scn.nextInt());
        
        No raiz = new No(matRaiz, null, args[0]);
        
        fechados.add(raiz);
        
        while(fechados.isEmpty() == false) {
            No n = fechados.poll();
            if (!abertos.containsKey(n.vetor.hashCode())) { 
                abertos.put(n.vetor.hashCode(), n);
                n.sucessores(fechados, args[0]);
            }
        }
    }

    private static class No implements Comparable<No>{

        List<Integer> vetor;
        int posVazio;
        No pai;
        int nivel;
        float heuristica;
        
        public No(List<Integer> vetor, No pai, String args) {
            String heuris = args;
            this.vetor = vetor;
            this.posVazio = this.vetor.indexOf(0);
            this.pai = pai;
            if (pai != null) {
                this.nivel = this.pai.nivel + 1;     //Quantidade de movimentos realizados ou altura da arvore
            }
            else this.nivel = 0;                     //Se pai for Vazio, entao seu nivel eh zerado
            switch(heuris){
                case "1":
                    this.heuristica = h1() + this.nivel;
                    if (this.heuristica - this.nivel == 0) { //Quando as heuristicas h1 h2 h3 h4 h5 retornam 0;
                        System.out.print(this.nivel);
                        System.exit(0);
                    }
                    break;
                case "2":
                    this.heuristica = h2() + this.nivel;
                    if (this.heuristica - this.nivel == 0) { //Quando as heuristicas h1 h2 h3 h4 h5 retornam 0;
                        System.out.print(this.nivel);
                        System.exit(0);
                    }
                    break;
                case "3":
                    this.heuristica = h3() + this.nivel;
                    if (this.heuristica - this.nivel == 0) { //Quando as heuristicas h1 h2 h3 h4 h5 retornam 0;
                        System.out.print(this.nivel);
                        System.exit(0);
                    }
                    break;
                case "4":
                    this.heuristica = h4() + this.nivel;
                    if (this.heuristica - this.nivel == 0) { //Quando as heuristicas h1 h2 h3 h4 h5 retornam 0;
                        System.out.print(this.nivel);
                        System.exit(0);
                    }
                    break;
                case "5":
                    this.heuristica = h5() + this.nivel;
                    if (this.heuristica - this.nivel == 0) { //Quando as heuristicas h1 h2 h3 h4 h5 retornam 0;
                        System.out.print(this.nivel);
                        System.exit(0);
                    }
                    break;
            }
        }

        private void sucessores(PriorityQueue<No> fechados, String args) {
            int space = this.posVazio;
            
            if (space != 3 && space != 7 && space != 11 && space != 15) {// difertente da parede da direita entao posso mover para direita
                fechados.add(new No(moveVazio(0, 1), this, args));
            }
           if (space != 0 && space != 4 && space != 8 && space != 12) {// difertente da parede da esquerda entao posso mover para esquerda
                fechados.add(new No(moveVazio(0, -1), this, args));
            }
            if (space > 3) {// difente da parede de cima entao eu posso mover para cima 
                fechados.add(new No(moveVazio(-1, 0), this, args));
            }
            if (space < 12) {// difente da parede de baixo entao eu posso mover para para baixo 
                fechados.add(new No(moveVazio(1, 0), this, args));
            }
        }
        
        
        private int h1() {
            int count = 0;
            int p = 1;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                   if (i != j || i < 3 || j < 3)
                       if (p != this.vetor.get(i * 4 + j)) count++;
                   p++;
                }
            }
            return count;
        }
        
        private int h2() {
            int count = 0;
            int anterior = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    if (i != 0 || j != 0)
                        if (this.vetor.get(i * 4 + j) != 0 && (this.vetor.get(i * 4 + j) - anterior != 1)) count++;
                    anterior = this.vetor.get(i * 4 + j);
                }
            }
            return count;
        }
        
        private int h3() {
            int distRet = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if(this.vetor.get(i*4 + j) == 0){
                        distRet += Math.abs(i -(int)(15/4));
                        distRet += Math.abs(j -(15%4));
                    }
                    else{
                        distRet += Math.abs(i -(int)(this.vetor.get(j*4 + i)-1)/4);
                        distRet += Math.abs(j -(int)(this.vetor.get(j*4 + i)-1)%4);
                    }
                }
            }
            return distRet;
        }
        
        private int h4() {
            float h;
            h = (float) (0.2*h1() + 0.3*h2() + 0.5*h3());
            return (int) h;
        }
        
        private int h5() {
            int h = Math.max(h1(), h2());
            h = Math.max(h, h3());
            return h;
        }
        
        private List<Integer> moveVazio(int Y, int X) {
            int newPos = this.posVazio + (Y * 4 + X);
            List<Integer> v = new ArrayList<>(this.vetor);
            int temp = v.get(newPos);
            v.remove(this.posVazio);
            v.add(this.posVazio, temp);
            v.remove(newPos);
            v.add(newPos, 0);
            
            return v;
        }

        @Override
        public int compareTo(No o) {
            return (int) (this.heuristica - o.heuristica);
        }
    }
}