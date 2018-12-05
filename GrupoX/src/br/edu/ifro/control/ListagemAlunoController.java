package br.edu.ifro.control;

import br.edu.ifro.model.Aluno;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ListagemAlunoController implements Initializable {

    @FXML
    private TableView<?> tbAlunos;
    @FXML
    private ComboBox<?> cbAlunos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listar();
    }    

    @FXML
    private void editar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/br/edu/ifro/view/Aluno.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        
        AlunoController c = fxmlLoader.getController();
        c.editarAluno((Aluno) tbAlunos.getSelectionModel().getSelectedItem());
        
        stage.setTitle("Cadastrar aluno");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void excluir(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        Aluno aluno = (Aluno) tbAlunos.getSelectionModel().getSelectedItem();
        
        Query query = em.createQuery("SELECT a FROM Aluno as a WHERE a.id = :id");
        query.setParameter("id", aluno.getId());
               
        Aluno a = (Aluno) query.getSingleResult();
        
        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        listar();
    }
    
    private void listar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
                
        // import javax.persistence.Query;
        Query query = em.createQuery("SELECT a FROM Aluno as a");
        // import java.util.List;
        // import br.edu.ifro.model.Aluno;
        List<Aluno> alunos = query.getResultList();
        // import javafx.collections.ObservableList;
        // import javafx.collections.FXCollections;
        ObservableList oAlunos = FXCollections.observableArrayList(alunos);                                 
        cbAlunos.setItems(oAlunos);
        tbAlunos.setItems(oAlunos);
    }
    
}
