/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Cliente;
import br.edu.ifro.model.Emprestimo;
import br.edu.ifro.model.Funcionario;
import br.edu.ifro.model.Obra;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EmprestimoController implements Initializable {

    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<Funcionario> cbFuncionario;
    @FXML
    private ComboBox<Obra> cbObras;
    @FXML
    private TableView<Obra> tbObras;
    @FXML
    private Button btnCancelar;
    
    private Emprestimo emprestimo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT a FROM Cliente a");
        Query query2 = em.createQuery("SELECT a FROM Funcionario a");
        Query query3 = em.createQuery("SELECT a FROM Obra a");
        List clientes = query.getResultList();
        List funcionarios = query2.getResultList();
        List obras = query3.getResultList();
        cbCliente.setItems(FXCollections.observableArrayList(clientes));
        cbFuncionario.setItems(FXCollections.observableArrayList(funcionarios));
        cbObras.setItems(FXCollections.observableArrayList(obras));
        
        emprestimo = new Emprestimo();
    }    
    
    @FXML
    private void adicionar(ActionEvent event) {
        emprestimo.addObra(cbObras.getSelectionModel().getSelectedItem());
        tbObras.setItems(FXCollections.observableArrayList(emprestimo.getObras()));
    }

    @FXML
    private void remover(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Obra");
        alert.setContentText("Deseja apagar a Obra selecionado?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        emprestimo.removeObra(cbObras.getSelectionModel().getSelectedItem());
        tbObras.setItems(FXCollections.observableArrayList(emprestimo.getObras()));
        } else {
            
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        emprestimo.setCliente(cbCliente.getSelectionModel().getSelectedItem());
        emprestimo.setFuncionario(cbFuncionario.getSelectionModel().getSelectedItem());
        
        em.getTransaction().begin();
        em.persist(emprestimo);
        em.getTransaction().commit();
        
        Stage stage2 = (Stage) btnCancelar.getScene().getWindow();
        stage2.close();
        limpar();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void limpar() {
        cbCliente.getSelectionModel().clearSelection();
        cbFuncionario.getSelectionModel().clearSelection();
        cbObras.getSelectionModel().clearSelection();
    }
}
