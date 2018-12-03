/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Autor;
import br.edu.ifro.model.Obra;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CadastrarObraController implements Initializable {

    @FXML
    private ComboBox<Autor> cbAutor;
    @FXML
    private TextField txtNome;
    @FXML
    private DatePicker dpData;
    @FXML
    private Button btnCancelar;
    
    private Obra obra;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Autor a");
        List autores = query.getResultList();
        
        cbAutor.setItems(FXCollections.observableArrayList(autores));
    }    

    public void editarObra(Obra obra) {
        this.obra = obra;
        txtNome.setText(obra.getNome());
        Date input = obra.getData();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpData.setValue(date);
        for (Autor e: cbAutor.getItems()){
            if (e.getId() == obra.getAutor().getId()){
                cbAutor.getSelectionModel().select(e);
            }
        }
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Obra obra1;
        
        if (obra != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Edição");
        alert.setContentText("Tem certeza que deseja editar a obra selecionada?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Query query = em.createQuery("SELECT a FROM Obra as a WHERE a.id = :id");
        query.setParameter("id", obra.getId());
        
        obra1 = (Obra) query.getSingleResult();
        obra1.setNome(txtNome.getText());
        LocalDate localDate = dpData.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        obra1.setData(date);
        obra1.setAutor(cbAutor.getSelectionModel().getSelectedItem());
        
        em.getTransaction().begin();
        em.persist(obra1);
        em.getTransaction().commit();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Edição de Obras");
        alert2.setHeaderText(null);
        alert2.setContentText("Obra editada com Sucesso!");

        alert2.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

        } else {
            
        }
        } else { 
    
        obra1 = new Obra(); 
        
        obra1.setNome(txtNome.getText());
        LocalDate localDate = dpData.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        obra1.setData(date);
        obra1.setAutor(cbAutor.getSelectionModel().getSelectedItem());
        
        em.getTransaction().begin();
        em.persist(obra1);
        em.getTransaction().commit();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Obra");
        alert.setHeaderText(null);
        alert.setContentText("Obra cadastrada com Sucesso!");

        alert.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        limpar();
        }
        }

    @FXML
    private void limpar() {
        txtNome.setText("");
        dpData.setValue(null);
        cbAutor.getSelectionModel().clearSelection();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
}
