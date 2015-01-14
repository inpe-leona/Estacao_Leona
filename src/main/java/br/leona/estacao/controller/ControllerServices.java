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
import br.leona.hardware.model.Servico;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
     * @param capturarImagens
     * @return 
     * @throws java.io.IOException 
     * @throws javax.media.NoPlayerException 
     * @throws javax.media.CannotRealizeException 
     */
    @WebMethod(operationName = "IniciarCaptura")
    public String Capturar(@WebParam(name = "capturarImagens") String capturarImagens) throws IOException, NoPlayerException, CannotRealizeException {
        System.out.println("Livia no controller services******************************************");
        cameraController.iniciarCamera();
        cameraController.iniciarCaptura(capturarImagens);
        return capturarImagens;
    }

    /**
     * Operação de Web service
     * @param status
     * @return 
     */
    @WebMethod(operationName = "PararCaptura")
    public String PararCaptura(@WebParam(name = "status") String status) {
        System.out.println("entrei em parar****************************************");
         cameraController.pararCaptura(status);
        return status;

    }

    @WebMethod(operationName = "retornarListaStatus")
    public List<Servico> retornarListaStatus() {
        List<Servico> list = new ArrayList<>();        
        try { 
		File file = new File("c:/hardware/serialPort.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Servico.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Servico service = (Servico) jaxbUnmarshaller.unmarshal(file);
		System.out.println(service);
                list.add(service);
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }   
        return list;
    }
}
