/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Obra;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ListagemObraController implements Initializable {

    @FXML
    private TableView<Obra> tbObras;
    @FXML
    private Button btnFechar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Obra a");
        List obras = query.getResultList();
        
        tbObras.setItems(FXCollections.observableArrayList(obras));
    }    

    @FXML
    private void excluir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setContentText("Tem certeza que deseja excluir a Obra selecionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        EntityManagerFactory emf  = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Obra obra = (Obra) tbObras.getSelectionModel() .getSelectedItem(); 
        
        Query query = em.createQuery("SELECT a FROM Obra as a WHERE a.id = :id");
        query.setParameter("id", obra.getId());
        
        Obra c = (Obra) query.getSingleResult();
        
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        atualizarTabela();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Exclusão de Obra");
        alert2.setHeaderText(null);
        alert2.setContentText("Obra excluida com sucesso!");
        alert2.showAndWait();

        } else {

        }
    }

    @FXML
    private void editar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/br/edu/ifro/view/CadastrarObra.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        CadastrarObraController c = fxmlLoader.getController();
        c.editarObra((Obra) tbObras.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        
        stage.setTitle("Editar Obra");
        stage.setScene(scene);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                atualizarTabela();
            }
        }); 
        stage.show();
    }

    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
    
    private void atualizarTabela() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Obra a");
        List obras = query.getResultList();
        
        tbObras.setItems(FXCollections.observableArrayList(obras));
    }
}
