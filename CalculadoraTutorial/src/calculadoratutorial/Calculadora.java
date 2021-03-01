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
public class Calculadora {
    GrupoSignos[]grupo_signos;
    char []signos={'^','/','*','-','+'};
    
   
    
    public Calculadora(){
      grupo_signos=new GrupoSignos[3];
      grupo_signos[0]=new GrupoSignos('^');
      grupo_signos[1]=new GrupoSignos('/','*');
      grupo_signos[2]=new GrupoSignos('-','+');
    }

    
    public static double Resolver(String operacion_completa){
        Calculadora c=new Calculadora();
        while(c.BuscarPrimerSigno(operacion_completa)!=-1){
            operacion_completa=c.BuscarOperacion(operacion_completa);//resuelve una operacion encontrada
        }
        //
        
        return Double.parseDouble(operacion_completa);
    }
        //Metodo que busca en la operacion completa, la primera operacion y la resuelve
    //ej: 5+5^3*2 resuelve el 5^3 y devuelve 5+125*2   5
    public String BuscarOperacion(String operacion_completa){
      
        for(int i=0;i<grupo_signos.length;i++){
            int indice_signo=-1;
            //buscare los grupos de signos en orden jerargico
            indice_signo=grupo_signos[i].findSigne(operacion_completa);
            
            if(indice_signo!=-1){
                //SI PILLO OPERACION
                //ej:operacion -5+5^3*2
                String inicio=operacion_completa.substring(0,indice_signo);//-5+5
                String fin=operacion_completa.substring(indice_signo+1,operacion_completa.length());//3*2
                
                String numero1=ObtenerUltimoNumero(inicio);// 5
                String numero2=ObtenerPrimerNumero(fin);//3
                
                Double n1,n2,r=404.0;
                n1=Double.parseDouble(numero1);
                n2=Double.parseDouble(numero2);
                
                switch(grupo_signos[i].obtenerSignoEncontrado()){
                    case '^':r=Math.pow(n1,n2);break;
                    case '/':r=n1/n2;break;
                    case '*':r=n1*n2;break;
                    case '-':r=n1-n2;break;
                    case '+':r=n1+n2;break;
                }
                //-5+5^3*2 remplazamos -5+125*2
                operacion_completa=operacion_completa.replaceFirst(
                        Pattern.quote(numero1+grupo_signos[i].obtenerSignoEncontrado()+numero2),
                        ""+r);
                return operacion_completa;
            }
        }
        return operacion_completa;
    }
    
    
    //-5-4--4 obtiene -4
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
    ////-5-4--4 obtiene -5
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
    
    
        public class GrupoSignos{
        char[]grupo;
        char signoPillado='0';
        public GrupoSignos(char ...grupo){
        this.grupo=grupo;
        }
        
        public int findSigne(String operacion){
            for(int i=1;i<operacion.length();i++){
                for(char c:grupo)
                if(operacion.charAt(i)==c){
                    signoPillado=c;
                    return i;
                }
            }
            return -1;
        }
        public char obtenerSignoEncontrado(){return signoPillado;}
    }
    
}
