
import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {
    	 
    	 this.w = w;
         this.r = (int) (w-1)/2 +1;
         //this.r = (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            //this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
            this.A = generateRandom(power2(w-1), power2(w),seed);
         }
         else{
            this.A = A;
         }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
     /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
     
     
     /**Implements the hash function g(k)*/
     public int probe(int key, int i) {
    	 int chain = ((A * key)%power2(w)) >> (w-r);
    	 int hash = (chain+i)%m; 	
    	 return hash;       	
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
                 	
        	int i = 0;
        	while (i != m) {
        		int index = probe(key, i);
        		// nothing needed, no collisions
        		if (Table[index] == key) {
        			break;
        		}
        		// if slot is empty, insert key
        		else if (Table[index] == -1 || Table[index] == -2) {
        			Table[index] = key;
        		}
        		// slot is occupied
        		// recalculate index 
        		// collision num increased by 1
        		else {
        			i += 1;
        		}	
        	}
            return i;  
            
        }
        
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){

            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            

         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
        	
        	int i = 0;
        	while (i != m) {
        		// find bucket using hash function
        		int index = probe(key, i);
        		// found key
        		if (Table[index] == key) {
        			// set tombstone value - indicates deletion
        			Table[index] = -2;
        			break;
        		}
        		// encountered tombstone, keep searching 
        		else if (Table[index] == -2) {
        			i += 1;
        		}
        		// encountered empty slot, end search
        		else if (Table[index] == -1) {
        			break;
        		}
        		// if collision
        		else {
        			i += 1;
        		}
        	}        
            return i;
        }
}
