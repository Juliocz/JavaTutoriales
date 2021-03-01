/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoratutorial;

import java.util.regex.Pattern;

/**
 *
 * @author pc 9
 */
public class CalculadoraBasura {
    char[]signos={'^','/','*','-','+'};
    
    public CalculadoraBasura(){}
    
    

    
    public static double Resolver(String operacion_completa){
        CalculadoraBasura c=new CalculadoraBasura();
        
        while(c.BuscarPrimerSigno(operacion_completa)!=-1){
            operacion_completa=c.BuscarOperacion(operacion_completa);
        }
        return Double.parseDouble(operacion_completa);
    }
        //Metodo que busca en la operacion completa, la primera operacion y la resuelve
    //ej: 5+5^3*2 resuelve el 5^3 y devuelve 5+125*2   5
    public String BuscarOperacion(String operacion_completa){
        
       for(int i=0;i<signos.length;i++){//empieza a buscar de 1 por que si empieza -1 no leera el primer signo
           //recorreremos todos los signos en orden jerarquico
           //int indice_signo=operacion_completa.indexOf(signos[i]);//
           
           
           int indice_signo=-1;
           //si el signo es el primer indice 0, no es operacion es un numero, buscara el signo siguiente
         //buscamos el signo
               for(int j=1;j<operacion_completa.length();j++)
                   if(operacion_completa.charAt(j)==signos[i]){
                       indice_signo=j;
               break;
                   }
           
           if(indice_signo!=-1)//si encontro un signo
           {    //signos[i]
                System.out.println("indice signo operacion: "+indice_signo);
               
               String inicio=operacion_completa.substring(0, indice_signo);//
               String fin=operacion_completa.substring(indice_signo+1,operacion_completa.length());//
               
               System.out.println("inicio: "+inicio);
              System.out.println("fin: "+fin);
               
               
               String numero1,numero2;
               numero1=ObtenerUltimoNumero(inicio);//5+1+1+3 obtendre 3
               numero2=ObtenerPrimerNumero(fin);//5+1+1+3 obtendre 5
               
               System.out.println("Numero1s: "+numero1);
               System.out.println("Numero2s: "+numero2);
               
              Double n1,n2,r;
              r=1.0;
              n1=Double.parseDouble(numero1);
              n2=Double.parseDouble(numero2);
               
              System.out.println(""+n1);
              System.out.println(""+n2);
              
               switch(signos[i]){
                   case '^':r=Math.pow(n1, n2);break;
                   case '/':r=n1/n2;break;
                   case '*':r=n1*n2;break;
                   case '-':r=n1-n2;break;
                   case '+':r=n1+n2;break;
               }
               System.out.println(""+r);
               operacion_completa=operacion_completa.replaceFirst(Pattern.quote(numero1+signos[i]+numero2),""+r);
               
               return operacion_completa;
           }
           
       }
       return operacion_completa;
    }
    
    
    public String ObtenerUltimoNumero(String operacion){
        int indiceUltimosigno=BuscarUltimoSigno(operacion);
        if(indiceUltimosigno==-1)return operacion;
        else {
            if(indiceUltimosigno==0)
            return operacion.substring(indiceUltimosigno,operacion.length());    
            else
            return operacion.substring(indiceUltimosigno+1,operacion.length());
        }  
    }
     public String ObtenerPrimerNumero(String operacion){
        int indicePrimersigno=BuscarPrimerSigno(operacion);
        if(indicePrimersigno==-1)return operacion;
        else {
            return operacion.substring(0,indicePrimersigno);
        }  
    }
    
    //5*5+1+1+1-2 devuelve *
    public int BuscarPrimerSigno(String operacion){
        for(int i=1;i<operacion.length();i++){//recorriendo la operacion ej:-3*2
            for(char s:signos){//buscando los signos
                if(operacion.charAt(i)==s)return i;
            }
        }
        return -1;
    }
    //5*5+1+1+1-2 devuelve -
    public int BuscarUltimoSigno(String operacion){
        int indexT=-10;
        int indexTF=-1;
        for(int i=operacion.length()-1;i>=0;i--){//recorriendo la operacion ej:3*2
            for(char s:signos){//buscando los signos
                if(operacion.charAt(i)==s){
                    
                    //+5--1
                    if(indexTF==-1)
                        indexTF=i;
                    else indexT=i;
                }
            }
        }
        //-4
 
        if(indexT+1==indexTF)
            return indexT;
        else 
            return indexTF;
    }
    
}
