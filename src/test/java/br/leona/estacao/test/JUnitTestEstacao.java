/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.test;

import br.leona.estacao.controller.ControllerServices;
import br.leona.hardware.model.Servico;
import java.io.IOException;
import java.util.List;
import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leona
 */
public class JUnitTestEstacao {    
    private static ControllerServices controllServices;  
    private static int valor = 0;
    
    public JUnitTestEstacao() {
        controllServices = new ControllerServices();  
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException, NoPlayerException, CannotRealizeException {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {   
     //   ligarCamera();
    //    controllServices.iniciarVideo();
     /*   controllServices.iniciarCaptura();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }     */
    }

    @After
    public void tearDown() {
    //   controllServices.pararCaptura();
//       desligarCamera();
       //reset();
    }
    
    //@Test
    public void reset(){
       assertEquals(1, controllServices.resetPantilt()); 
    }       
    
    //@Test
    public void ligarCamera(){
       assertEquals(1, controllServices.ligarCamera());
    }   
    
    //@Test
    public void desligarCamera(){
       assertEquals(0, controllServices.desligarCamera());
    }   
       //@Test
    public void calculo() { 
    int  graus = 22;
        assertEquals(1, controllServices.moverAzimute(graus));
    }
  
    //@Test
    public void close() { // recebe 1 ou 0
        assertEquals(1, controllServices.desligar());
    }
    
    //@Test
    public void foto() {
        System.out.println("oi: ");
        List<String> lS = controllServices.retornarNomesFotos("C:\\ProjetoLeona\\Evento_20150401_153640");
        System.out.println("Fotos: "+lS);
    }
     
     @Test
     public void listaServicos() {    
        List<Servico> list = controllServices.retornarListaStatus();
        assertNotNull(list);  
        for(int i=0; i < list.size(); i++){
            System.out.println(list.get(0).getName());
            System.out.println(list.get(0).getStatus());
        }
    }     
   
    //@Test
    public void moverElevacao(){
          assertEquals("1", controllServices.moverElevacao(10));
    }
    
     //@Test
    public void moverAzimute(){
          assertEquals("1", controllServices.moverAzimute(60));
    }
     
    //@Test
    public void retornarNomesFotos(){        
        controllServices.retornarNomesFotos("C:\\ProjetoLeona\\Evento_20150401_153640");
    }
    
    //@Test
    public void removerFotos(){        
        controllServices.removerFotos("D:\\ProjetoLeona\\Evento_20150401_103444");
    }
    
     @Test
    public void removerDiretorio(){        
        controllServices.removerDiretorio("D:\\ProjetoLeona\\Evento_20150401_153640");        
    }       
}

