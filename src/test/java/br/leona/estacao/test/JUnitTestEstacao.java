/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.test;

import br.leona.estacao.controller.ControllerServices;
import br.leona.hardware.model.Servico;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leona_Lenovo
 */
public class JUnitTestEstacao {    
    private static ControllerServices cs;
    private int valor = 0;
    
    public JUnitTestEstacao() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        cs = new ControllerServices();   
        cs.Ligar();        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //LigarDesligarCamera();
        ligarCamera();
        cs.Capturar();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
       
    }

    @After
    public void tearDown() {
       cs.PararCaptura();
       //LigarDesligarCamera();
       DesligarCamera();
       reset();
    }
    
    //@Test
    public void reset(){
        try {
            assertEquals(1, cs.ResetPantilt());            
        } catch (InterruptedException ex) {
            Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }       
    
    //@Test
    public void LigarDesligarCamera()  { // recebe 1 ou 0        
        if(valor == 0){
            valor = 1;        
            try {
                assertEquals(1, cs.LigarDesligarCamera(valor));
            } catch (InterruptedException ex) {
                Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            valor = 0;        
            try {
                assertEquals(0, cs.LigarDesligarCamera(valor));
            } catch (InterruptedException ex) {
                Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    //@Test
    public void ligarCamera(){
       assertEquals(0, cs.LigarCamera());
    }   
    
    //@Test
    public void DesligarCamera(){
       assertEquals(0, cs.DesligarCamera());
    }   
      
    @Test
    public void left() {
        int graus = 60;
        try {
            assertEquals(1, cs.moverEsquerda(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void right() {
        int graus = 60;
        try {
            assertEquals(1, cs.MoverDireita(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void up(){
        int graus = 30;
        try {
            assertEquals(1, cs.MoverCima(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void down() {
        int graus = 30;
        try {
            assertEquals(1, cs.MoverBaixo(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(JUnitTestEstacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    //@Test
    public void calculo() { 
    int  graus = 22;
        assertEquals(1, cs.moverAzimute(graus));
    }
  
    //@Test
    public void close() { // recebe 1 ou 0
        assertEquals(1, cs.Desligar());
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //@Test
     public void foto() {
         System.out.println("oi: ");
         List<String> lS = cs.retornarNomesFotos();
         System.out.println("Fotos: "+lS);
     }
     
     @Test
     public void listaServicos() {    
        List<Servico> list = cs.retornarListaStatus();
        assertNotNull(list);  
        for(int i=0; i < list.size(); i++){
            System.out.println(list.get(0).getName());
            System.out.println(list.get(0).getStatus());
        }
    }
    
}
