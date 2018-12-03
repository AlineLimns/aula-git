/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Emprestimo;
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

public class ListagemEmprestimoController implements Initializable {

    @FXML
    private TableView<Emprestimo> tbEmprestimos;
    @FXML
    private Button btnFechar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Emprestimo a");
        List emprestimos = query.getResultList();
        
        tbEmprestimos.setItems(FXCollections.observableArrayList(emprestimos));
    }    

    @FXML
    private void excluir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setContentText("Tem certeza que deseja excluir o Empréstimo selecionado?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        EntityManagerFactory emf  = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        Emprestimo emprestimo = (Emprestimo) tbEmprestimos.getSelectionModel() .getSelectedItem(); 
        
        Query query = em.createQuery("SELECT a FROM Emprestimo as a WHERE a.id = :id");
        query.setParameter("id", emprestimo.getId());
        
        Emprestimo c = (Emprestimo) query.getSingleResult();
        
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        atualizarTabela();
        
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Exclusão de Empréstimo");
        alert2.setHeaderText(null);
        alert2.setContentText("Empréstimo excluido com sucesso!");
        alert2.showAndWait();

        } else {

        }
    }

    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
    
    private void atualizarTabela() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Emprestimo a");
        List emprestimos = query.getResultList();
        
        tbEmprestimos.setItems(FXCollections.observableArrayList(emprestimos));
    }
}