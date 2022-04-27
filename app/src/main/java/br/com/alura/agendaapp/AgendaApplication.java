package br.com.alura.agendaapp;

import android.app.Application;

import br.com.alura.agendaapp.dao.AlunoDAO;
import br.com.alura.agendaapp.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Alex", "1122223333", "alex@alura.com.br"));
        dao.salva(new Aluno("Fran", "1122223333", "fran@alura.com.br"));
    }
}
