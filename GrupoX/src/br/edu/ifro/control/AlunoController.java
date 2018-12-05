/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifro.control;

import br.edu.ifro.model.Aluno;
import br.edu.ifro.model.Cidade;
import br.eti.diegofonseca.MaskFieldUtil;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author erica
 */
public class AlunoController implements Initializable {

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtTelefone;
    
    private Aluno aluno;
    
    @FXML
    private ComboBox<Cidade> cbCidade;
    
    public void editarAluno(Aluno aluno) {
        this.aluno = aluno;
        txtNome.setText(aluno.getNome());
        txtTelefone.setText(aluno.getTelefone());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.foneField(txtTelefone);    
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT c FROM Cidade c ");
        List cidades = query.getResultList();
        
        cbCidade.setItems(FXCollections.observableArrayList(cidades));
        
    }    

    @FXML
    private void salvar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Aluno aluno1;
        if (aluno != null) {            
            Query query = em.createQuery("SELECT a FROM Aluno as a WHERE a.id = :id");
            query.setParameter("id", aluno.getId());

            aluno1 = (Aluno) query.getSingleResult();
        } 
        else {
            aluno1 = new Aluno();
        }     
        
        aluno1.setNome(txtNome.getText());
        // com mascara
        aluno1.setTelefone(txtTelefone.getText());
        
        aluno1.setCidade(cbCidade.getSelectionModel().getSelectedItem());
        // sem mascara
        //aluno1.setTelefone(MaskFieldUtil.onlyAlfaNumericValue(txtTelefone));
        
        em.getTransaction().begin();
        
        em.persist(aluno1);
        
        em.getTransaction().commit();
        
    }

    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
}
