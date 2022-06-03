package br.com.alura.agendaapp.ui.activity;

import static br.com.alura.agendaapp.ui.activity.ListaAlunosActivity.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.agendaapp.R;
import br.com.alura.agendaapp.database.AgendaDatabase;
import br.com.alura.agendaapp.database.TelefoneDAO;
import br.com.alura.agendaapp.database.dao.AlunoDAO;
import br.com.alura.agendaapp.model.Aluno;
import br.com.alura.agendaapp.model.Telefone;
import br.com.alura.agendaapp.model.TipoTelefone;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private AlunoDAO alunoDAO;
    private Aluno aluno;
    private TelefoneDAO telefoneDAO;
    List<Telefone> telefoneDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        AgendaDatabase database = AgendaDatabase.getInstance(this);
        alunoDAO = database.getRoomAlunoDAO();
        telefoneDAO = database.getTelefoneDAO();
        inicializacaoDosCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(ListaAlunosActivity.CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposDeTelefone();
    }

    private void preencheCamposDeTelefone() {
        telefoneDoAluno = telefoneDAO.buscaTodosTelefonesDoAluno(aluno.getId());
        for (Telefone telefone :
                telefoneDoAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO) {
                campoTelefoneFixo.setText(telefone.getNumero());
            } else {
                campoTelefoneCelular.setText(telefone.getNumero());
            }
        }
    }

    private void finalizaFormulario() {
        preencheAluno();

        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

        if (aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {
            salvaAluno(telefoneFixo, telefoneCelular);
        }
        finish();
    }

    @NonNull
    private Telefone criaTelefone(EditText campoTelefoneFixo, TipoTelefone fixo) {
        String numeroFixo = campoTelefoneFixo.getText().toString();
        return new Telefone(numeroFixo, fixo);
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        int alunoId = alunoDAO.salva(aluno).intValue();
        vinculaAlinoComTelefone(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular);
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        alunoDAO.edita(aluno);
        vinculaAlinoComTelefone(aluno.getId(), telefoneFixo, telefoneCelular);
        atualizaIdsDosTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
    }

    private void atualizaIdsDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone :
                telefoneDoAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO) {
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }

    private void vinculaAlinoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefoneFixo = campoTelefoneFixo.getText().toString();
        String telefoneCelular = campoTelefoneCelular.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
//        aluno.setTelefoneFixo(telefoneFixo);
//        aluno.setTelefoneCelular(telefoneCelular);
        aluno.setEmail(email);
    }
}