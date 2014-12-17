/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.estacao.controller;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import br.leona.hardware.controller.PTZController;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leona
 *
 */
@WebService(serviceName = "ControllerServices")
public class ControllerServices {

    PTZController pantilt = new PTZController();

    @WebMethod(operationName = "MoverEsquerda")
    public int moverEsquerda(@WebParam(name = "graus") int graus) throws InterruptedException {
        return pantilt.left(graus);
    }

    @WebMethod(operationName = "MoverCima")
    public int MoverCima(@WebParam(name = "graus") int graus) throws InterruptedException {
        return pantilt.up(graus);
    }

    @WebMethod(operationName = "MoverDireita")
    public int MoverDireita(@WebParam(name = "graus") int graus) throws InterruptedException {
        return pantilt.right(graus);
    }

    @WebMethod(operationName = "MoverBaixo")
    public int MoverBaixo(@WebParam(name = "graus") int graus) throws InterruptedException {
        //TODO write your implementation code here:
        return pantilt.down(graus);
    }

    @WebMethod(operationName = "LigarDesligarCamera")
    public int LigarDesligarCamera(@WebParam(name = "valor") int valor) throws InterruptedException {
        return pantilt.camera(valor);

    }

    @WebMethod(operationName = "ResetPantilt")
    public int ResetPantilt() throws InterruptedException {
        return pantilt.resetPantilt();

    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "moverAzimute")
    public String moverAzimute(@WebParam(name = "graus") int graus) {        
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "azimute"));
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "moverElevacao")
    public String moverElevacao(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "elevacao"));
    }

    @WebMethod(operationName = "retornarNomesFotos")
    public List<String> retornarNomesFotos() {
        List<String> listS = new ArrayList<>();
        // diretório que será listado.  
        File baseFolder = new File("C:\\ProjetoLeona\\Visualizar\\Evento11122014101708");  

        // obtem a lista de arquivos  
        File[] files = baseFolder.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            File file = files[i]; 
            String name = file.getName();  
                listS.add(name);
                System.out.println(name); 
            
        }  
        return listS;
    }
    
}
