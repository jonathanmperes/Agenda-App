package br.com.alura.agendaapp.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agendaapp.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();

    public void salva(Aluno aluno) { alunos.add(aluno); }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }
}

