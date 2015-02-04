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
import br.leona.hardware.camera.CameraController;
import br.leona.hardware.file.FileXML;
import br.leona.hardware.model.Servico;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;

/**
 *
 * @author leona
 *
 */
@WebService(serviceName = "ControllerServices")
public class ControllerServices {  
    PTZController pantilt = new PTZController();
    CameraController cameraController = new CameraController();
      
    
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
        
    @WebMethod(operationName = "LigarCamera")
    public int LigarCamera() {
        int result = pantilt.cameraOn();
        if(result != 0) System.out.println("ERRO ao LIGAR CAMERA");
        return result;
    }
    
    @WebMethod(operationName = "DesligarCamera")
    public int DesligarCamera() {
        int result = pantilt.cameraOff();
        if(result != 0) System.out.println("ERRO ao DESLIGAR CAMERA");
        return result;
    }

    @WebMethod(operationName = "ResetPantilt")
    public int ResetPantilt() throws InterruptedException {
        return pantilt.resetPantilt();
    }

    /**
     * Operação de Web service
     * @param graus
     * @return 
     */
    @WebMethod(operationName = "moverAzimute")
    public String moverAzimute(@WebParam(name = "graus") int graus) {        
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "azimute"));
    }

    /**
     * Operação de Web service
     * @param graus
     * @return 
     */
    @WebMethod(operationName = "moverElevacao")
    public String moverElevacao(@WebParam(name = "graus") int graus) {
        return Integer.toString(pantilt.calculoAzimuteElevacao(graus, "elevacao"));
    }

    @WebMethod(operationName = "retornarNomesFotos")
    public List<String> retornarNomesFotos() {
        List<String> listS = new ArrayList<>();
        // diretório que será listado.  
        File baseFolder = new File("C:\\ProjetoLeona\\Evento11122014101708");  

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

    /**
     * Operação de Web service    
     */
    @WebMethod
    public void Capturar()  {
        System.out.println("Livia no controller services******************************************");
        cameraController.iniciarCaptura();
    }

    /**
     * Operação de Web service
     */
    @WebMethod
    public void PararCaptura() {
        System.out.println("entrei em parar****************************************");
        cameraController.pararCaptura();
    }

    @WebMethod(operationName = "retornarListaStatus")
    public List<Servico> retornarListaStatus() {
        List<Servico> list = new ArrayList<>();        
        FileXML fileXML = new FileXML();
        Servico servico = fileXML.readFile("c:/hardware/serialPort.xml");
        list.add(servico);
        return list;
    }
    
    @WebMethod(operationName = "desligar")
    public int Desligar() {
        System.out.println("entrei em parar****************************************");
        return pantilt.close();
    }
     
    @WebMethod(operationName = "ligar")
    public void Ligar() {   
        try {
            cameraController.iniciarCamera();
        } catch (IOException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayerException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CannotRealizeException ex) {
            Logger.getLogger(ControllerServices.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
