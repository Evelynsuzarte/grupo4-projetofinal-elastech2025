<div id="inicio">
    <h1 align="center">Plataforma de estudos EAD - Projeto final ElasTech 2025 </h1>
    <p align="center">
        <img src="http://img.shields.io/static/v1?label=STATUS&message=Concluido&color=GREEN&style=for-the-badge"/>
    </p>
	<p align="justify"> 
		Este projeto foi desenvolvido para a avaliação final do curso ElasTech de Java Fullstack, banco de dados e Spring Boot.
	</p><br>
</div>

<div id="sumario">
    <h1>Sumário</h1>
	<ul>
		<li><a href="#inicio"> <b>Início</b></li>
        <li><a href="#objetivo"> <b>Objetivo</b></li>
        <li><a href="#equipe"> <b>Equipe de Desenvolvimento</b></li>
        <li><a href="#tecnologias"> <b>Tecnologias utilizadas</b> </a> </li>
        <li><a href="#execucao"> <b>Configurando e executando o projeto</b> </a> </li>
        <li><a href="#endpoints"> <b>Endpoints da API</b> </a> </li>
        <li><a href="#entidades"> <b>Entidades do banco de dados</b> </a> </li>
		<li><a href="#implementacao"> <b>Implementação</b> </a> </li>
	</ul>	
</div>

<div id="objetivo">
    <h1>Objetivo</h1>
    <p>Projetar e construir uma plataforma EAD funcional. O sistema deve permitir que usuários com perfil de Professor criem e gerenciem seus cursos. Usuários com perfil de Aluno devem ser capazes de visualizar os cursos disponíveis e se matricular naqueles que desejarem. A plataforma deve conter uma área "Meus Cursos" para os alunos e uma área de "Alunos Matriculados" para os professores acompanharem suas turmas.
</p>
</div>


<div id="equipe">
    <h1>Equipe de Desenvolvimento</h1>
    <ul>
		<li><a href="https://github.com/anajulia-ssl"> Ana Julia Souza Silva Leite</li>
		<li><a href="https://github.com/car0l15"> Caroline Vitória Bispo Nepomuceno</a></li>
        <li><a href="https://github.com/evelynsuzarte"> Evelyn Suzarte Fernandes</a></li>
        <li><a href="https://github.com/Camargoge"> Gezielli Camargo Salles</a></li>
        <li><a href="https://github.com/keyllacaratin-commits"> Keylla Nicole Caratin Misato</a></li>
        <li><a href="https://github.com/LeideSilva10"> Leide Oliveira Silva</a></li>
        <li><a href="https://github.com/saraalmada"> Sara de Almada Torres</a></li>
	</ul>
</div>

<div id="tecnologias">
	<h1> Tecnologias e ferramentas utilizadas </h1>
	<ul>
        <li>Java versão JDK 21</li>
        <li>Spring Boot</li>
        <li>Spring WEB</li>
        <li>Spring Data JPA</li>
        <li>Hibernate</li>
        <li>Html + CSS + JavaScript</li>
        <li>MVC (Model View Controller)</li>
        <li>MySQL</li>
        <li>Bean Validation</li>
        <li>API Rest</li>
        <li>Eclipse e IntelliJ</li>
	</ul>	
</div>



<div id="execucao">
    <h1>Configurando e executando o projeto</h1>
    <p>Para realizar a configuração deste projeto siga as etapas a seguir:</p>
    <h3>Realize o download do projeto</h3>
    <p><code>$ git clone https://github.com/Evelynsuzarte/grupo4-projetofinal-elastech2025</code></p>
    <h3>Abra a pasta do projeto usando a IDE de preferência</h3>
    <p>Importe o projeto na IDE e execute o seguite comando no terminal :</p>
    <p><code>mvn clean install</code></p>
    <p><code>mvn spring-boot:run</code></p>
    <h3>Executar o projeto:</h3>
    <p><code>PREENCHER AQUI OS DETALHES</code></p>
    <p>
        PREENCHER AQUI OS DETALHES
    </p>
    
<div id="endpoints">
    <h1>Endpoints</h1>
	<p><a href="http://localhost:8080/usuarios">Acesse clicando aqui</a></p>
    <p>O teste pode ser feito através do navegador ou de um programa para teste de API, como o Postman ou Insomnia.</p> 
    <p></p>
    <p>Nesse sistema é possível que um Professor possa criar, editar e visualizar seus próprios cursos.um Aluno possa se inscrever em um
    curso, o que deve gerar um novo registro de Matrícula. um para listar os cursos em que um aluno está matriculado e outro para listar os alunos matriculados em um curso específico de um professor.
    </p>
    <table border="1" align="center">
    <tr>
        <td>Endpoint</td>
        <td>Método</td>
        <td>Descrição</td>
        <td>Link</td>
    </tr>
    <tr>
        <td>/usuarios</td>
        <td>GET</td>
        <td>Retorna todos os alunos do banco de dados</td>
        <td>http://localhost:8080/usuarios</td>
    </tr>
    <tr>
        <td>/usuarios/{id}</td>
        <td>GET</td>
        <td>Retorna apenas um alunos do banco de dados</td>
        <td>http://localhost:8080/usuarios/{id}</td>
    </tr>
    <tr>
        <td>/usuarios/adicionar</td>
        <td>POST</td>
        <td>Retorna apenas um alunos do banco de dados</td>
        <td>http://localhost:8080/usuarios/adicionar</td>
    </tr>
</table>
    <h1><b>PREENCHER ENDPOINTS RESTANTES</b></h1>

<div id="entidades">
    <h1>Entidades do banco de dados</h1>
    <p>A seguir, a modelagem do banco de dados, tabelas com seus atributos e relacionamentos.
    </p>
    <table border="1" align="center">
    <tr>
        <td>Entidade</td>
        <td>Atributos</td>
        <td>Relacionamento</td>
    </tr>
    <tr>
        <td>Usuário</td>
        <td>id, nome, email, senha, perfil (ALUNO ou PROFESSOR)</td>
        <td>Quando for professor: um pra muitos - cursos criados
            Quando for aluno: um pra muitos - matrículas realizadas    
        </td>
    </tr>
    <tr>
        <td>Curso</td>
        <td>GET</td>
        <td>Retorna apenas um alunos do banco de dados</td>
    </tr>
    <tr>
        <td>Matricula</td>
        <td>POST</td>
        <td>Retorna apenas um alunos do banco de dados</td>
    </tr>
</table>
    <h1><b>PREENCHER ENTIDADES RESTANTES</b></h1>


<div id="implementacao">
    <h1>Metodologia</h1>
    <h1><b>ALTERAR TEXTOS ABAIXO</b></h1>
   <h3><p><b>FrontEnd</b></p></h3>
    <p align="justify"> 
       <h3><b>ALTERAR TEXTOS ABAIXO </b></h3>
    <p>   
    <p align="justify"> A partir da seleção do usuário, é enviado um código para a função "uartRasp(código)", essa função é encontrada na biblioteca importada "codigoUartRasp.h". Nessa biblioteca que criamos (também em C), é processada a UART da Raspberry, que é configurada a partir desta biblioteca.
    </p>
    <h3><p><b>Backend</b></p></h3>
    <p>
        No arquivo "SBC/codigoUartRasp.h", utilizamos as bibliotecas "fcntl.h" e "termios.h" para manipulação da UART.
    <p>   
    <p align="justify"> 
        Começamos tentando o acesso através da variável "uart0_filestream" utilizando a função "open()". da verificando se deu erro na abertura da UART, caso não ocorra erro, começamos a manipulação da UART. Utilizamos as flags para configuração do BaudRate, paridade e tamanho da mensagem.
    </p>
    <p align="justify"> 
        Em seguida, verificamos o envio da mensagem na UART e o recebimento, caso dê algum erro recebemos uma mensagem sinalizando, para confirmar que foi enviado e recebido corretamente nós recebemos uma mensagem e também o comprimento da mensagem.
    </p>
    <p>
        Para fazer o teste de entrada e saída de dados é necessário colocar a UART em loopback.
    </p>
    <h3><p><b>Banco de dados</b></p></h3>
    <p>
        Para manipulação da UART foi utilizada a linguagem de programação Verilog. Nos arquivos "FPGA/uart_fpga_transmissor.v" e "FPGA/uart_fpga_receptor.v", temos as variáveis para inicialização da UART, como o clock, start e os dados. 
    <p>   
    <p align="justify"> 
        Logo abaixo é iniciado o processo de envio ou recebimento de dados, determinando a frequência de clock e o BaudRate da UART. E em seguida é feita o envio ou recebimento dos dados, que são 10 bits.
    </p>
	
</div>
    
</div>
