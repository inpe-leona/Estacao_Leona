/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.test;

import br.leona.estacao.controller.ControllerServices;
import br.leona.hardware.model.Servico;
import java.util.List;
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
public class ListFotos {
    
    private static ControllerServices cs;
    
    public ListFotos() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        cs = new ControllerServices();                
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
