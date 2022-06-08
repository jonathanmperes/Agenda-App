package br.com.alura.agendaapp.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agendaapp.database.dao.AlunoDAO;
import br.com.alura.agendaapp.model.Aluno;
import br.com.alura.agendaapp.ui.adapter.ListaAlunosAdapter;

public class BuscaALunosTask extends AsyncTask<Void, Void, List<Aluno>> {
    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;

    public BuscaALunosTask(AlunoDAO dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Aluno> todosAlunos) {
        super.onPostExecute(todosAlunos);
        adapter.atuliza(todosAlunos);
    }
}
