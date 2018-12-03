/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Autor;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CadastrarAutorController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private Button btnCancelar;

    private Autor autor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void editarAutor(Autor autor){
        this.autor = autor;
        txtNome.setText(autor.getNome());
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Autor autor1;
        
        if (autor != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Edição");
        alert.setContentText("Tem certeza que deseja editar o Autor selecionada?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Query query = em.createQuery("SELECT a FROM Autor as a WHERE a.id = :id");
        query.setParameter("id", autor.getId());
        
        autor1 = (Autor) query.getSingleResult();
        autor1.setNome(txtNome.getText());
        
        em.getTransaction().begin();
        em.persist(autor1);
        em.getTransaction().commit();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Edição de Autor");
        alert2.setHeaderText(null);
        alert2.setContentText("Autor editada com Sucesso!");

        alert2.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

        } else {
            
        }
        } else { 
    
        autor1 = new Autor(); 
        
        autor1.setNome(txtNome.getText());
        
        em.getTransaction().begin();
        em.persist(autor1);
        em.getTransaction().commit();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Autor");
        alert.setHeaderText(null);
        alert.setContentText("Autor cadastrado com Sucesso!");

        alert.showAndWait();
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        }
    }
}
