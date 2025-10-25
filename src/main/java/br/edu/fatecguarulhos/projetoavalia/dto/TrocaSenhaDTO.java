package br.edu.fatecguarulhos.projetoavalia.dto;

public class TrocaSenhaDTO {
	
    private String novaSenha;
    private String confirmarSenha;
	
    public TrocaSenhaDTO() {

	}
    
    public TrocaSenhaDTO(String novaSenha, String confirmarSenha) {
		this.novaSenha = novaSenha;
		this.confirmarSenha = confirmarSenha;
	}

    
    
    
	public String getNovaSenha() {
		return novaSenha;
	}
	
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
}    
    
    

