package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import model.Funcionario;

public class Principal {
	public static void main(String[] args) {
		
//		3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
	    List<Funcionario> funcionarios = new ArrayList<>();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", dateFormatter), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", dateFormatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", dateFormatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", dateFormatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", dateFormatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", dateFormatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", dateFormatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", dateFormatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", dateFormatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", dateFormatter), new BigDecimal("2799.93"), "Gerente"));
        
//        3.2 – Remover o funcionário “João” da lista. 
        funcionarios.removeIf(f-> f.getNome().equals("João"));
        
/*       3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
        • informação de data deve ser exibido no formato dd/mm/aaaa;
        • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
*/            
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00", symbols);
        
        for(Funcionario f : funcionarios){
        	imprimeFuncionarios(f);
        }
        
//      3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10")); 
            f.setSalario(novoSalario);
        }
        
//      3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosAgrupadosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
            
//      3.6 – Imprimir os funcionários, agrupados por função.
        System.out.println();
        for (Map.Entry<String, List<Funcionario>> entrada : funcionariosAgrupadosPorFuncao.entrySet()) {
        	System.out.println("Função: " + entrada.getKey());
            for (Funcionario f : entrada.getValue()) {
                imprimeFuncionarios(f);
            }
                System.out.println(); 
            }
            
//		Observação não há requisito 3.7 segue diretamente para o 3.8!
            
//      3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        System.out.println();
        System.out.println("Funcionários que fazem aniversário no mês 10 e 12. ");
        for (Funcionario f : funcionarios) {
        	int mesNascimento = f.getDataNascimento().getMonthValue();
            if (mesNascimento == 10 || mesNascimento == 12) {
                imprimeFuncionarios(f);
            }
        }
            
//		3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        int maiorIdade = 0;
        Funcionario funcionarioMaisVelho = null;
            
        System.out.println();
        System.out.println("Funcionário com a maior idade: ");
        for (Funcionario f : funcionarios) {
        	int idade = Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
            if (idade > maiorIdade) {
            	maiorIdade = idade; 
                funcionarioMaisVelho = f;  
            }
        }
        System.out.println("Funcionário mais velho: " + funcionarioMaisVelho.getNome() + " - Idade: " + maiorIdade);

//      3.10 – Imprimir a lista de funcionários por ordem alfabética.
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
            
        System.out.println();
        System.out.println("Lista de funcionários por ordem alfabética: ");
        for (Funcionario f : funcionarios) {
        	imprimeFuncionarios(f);
        }
            
//    	3.11 – Imprimir o total dos salários dos funcionários.
        BigDecimal totalSalarios = BigDecimal.ZERO; 

        for (Funcionario f : funcionarios) {
        	totalSalarios = totalSalarios.add(f.getSalario());
        }
          
        String salarioFormatado = decimalFormatter.format(totalSalarios);
        System.out.println();
        System.out.println("Total dos salários: R$ " + salarioFormatado);
        
//		3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00"); 
            
        System.out.println("");
        System.out.println("Salários mínimos ganha cada funcionário:");
        for (Funcionario f : funcionarios) {
        	BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
           	String salariosMinimosFormatado = decimalFormatter.format(salariosMinimos);
           	System.out.println(f.getNome() + " - " + salariosMinimosFormatado + " salários mínimos.");
        }     
	}
	
	//Método que imprime funcionarios;
	private static void imprimeFuncionarios(Funcionario f){
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00", symbols);
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = f.getDataNascimento().format(dateFormatter);
		String salarioFormatado = decimalFormatter.format(f.getSalario());
		System.out.println(f.getNome() + " - " + dataFormatada + " - R$ " + salarioFormatado);
	}
}

