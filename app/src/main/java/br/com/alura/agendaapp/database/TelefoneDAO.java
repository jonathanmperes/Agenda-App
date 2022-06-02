package br.com.alura.agendaapp.database;

import androidx.room.Dao;
import androidx.room.Query;

import br.com.alura.agendaapp.model.Telefone;

@Dao
public interface TelefoneDAO {
    @Query("SELECT * FROM Telefone WHERE alunoId = :alunoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int alunoId);
}
