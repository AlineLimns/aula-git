/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Autor;
import br.edu.ifro.model.Obra;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CadastrarObrasController implements Initializable {

    @FXML
    private ComboBox<Autor> cbAutor;
    @FXML
    private TextField txtNome;
    @FXML
    private DatePicker dpData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Autor a");
        List autores = query.getResultList();
        
        cbAutor.setItems(FXCollections.observableArrayList(autores));
    }    

    @FXML
    private void salvar(ActionEvent event) {
        Obra obra = new Obra();
        obra.setNome(txtNome.getText());
        Date date = Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date sqlDate = new java.sql.Date(date.getTime());
        obra.setData(sqlDate);
        obra.setAutor(cbAutor.getSelectionModel().getSelectedItem());
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(obra);
        em.getTransaction().commit();
    }

    @FXML
    private void limpar(ActionEvent event) {
        txtNome.setText("");
        dpData.setValue(null);
        cbAutor.setSelectionModel(null);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
}
