(function () {	
	
    function Chamado(id, cliente, equipamento, descricaoProblema, descricaoSolucao, emissao, aprovacao, tecnico, gerente, tipo, status) {
        this.id = id;
        this.descricaoProblema = descricaoProblema;
        this.descricaoSolucao = descricaoSolucao;
        this.emissao = emissao;
        this.aprovacao = aprovacao;
        this.tipo = tipo;
        this.status = status;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.gerente = gerente;
        this.equipamento = equipamento;
        this.valorTotal = 0.0;
    }

    function chamadoControler() {
		var limitePagina = 10;
	    var pagina = 0;			
        var modeloLinhaTabela;
        var modeloLinhaDropDowncliente;
        var modeloLinhaDropDownEquipamento;
        var modeloLinhaDropDownTecnico;
        var modeloLinhaDropDownGerente;
	    var idRemover;

        function _carregar(pesquisar) {
            if (pesquisar) {
                if ($('#formPesquisar input[name=dadoPesquisa]').val()) {
                    var dadoPesquisa = $('#formPesquisar input[name=dadoPesquisa]').val();
                    $.getJSON('api/chamados/paginado/'+(pagina+1)+'/'+limitePagina+'/'+dadoPesquisa, function(data) {
                        if (data.length >= 0 || pagina > 0) {
                            _renderTable(data);
                        }
                        else{
                            showMessage('warning', 'Nada encontrado!');
                        }
                        $('#proximo').prop('disabled', data.length == 0 || data.length < limitePagina);
                    });
                }
            }
            else { 
                $.getJSON('api/chamados/paginado/'+(pagina+1)+'/'+limitePagina, function(data) {
                    if (data.length >= 0 || pagina > 0) {
                        _renderTable(data);   
                    }
                    $('#proximo').prop('disabled', data.length == 0 || data.length < limitePagina);
                });
            }
        }

        _carregar();

        function _pesquisar() {
            _carregar(true);
        }

        function _carregaDropDown(){
            $.getJSON('api/clientes', function(data) {
                if (data.length >= 0) {
                    var final = '';
                    modeloLinhaDropDowncliente = modeloLinhaDropDowncliente || $('select[name=cliente]').html();
                    for (var i = 0; i < data.length; i++) {
                        var res = modeloLinhaDropDowncliente;
                        var linha = data[i];
                        res = res.replace(/\(\(id\)\)/g, linha.id);
                        res = res.replace(/\(\(cliente\)\)/g,linha.nome);
                        final += res;
                    }
                    final = '<option value="0">Selecione o cliente</option>' + final;
                    $('select[name=cliente]').html(final);
                }
            });
            $.getJSON('api/equipamentos', function(data) {
                if (data.length >= 0) {
                    var final = '';
                    modeloLinhaDropDownEquipamento = modeloLinhaDropDownEquipamento || $('select[name=equipamento]').html();
                    for (var i = 0; i < data.length; i++) {
                        var res = modeloLinhaDropDownEquipamento;
                        var linha = data[i];
                        res = res.replace(/\(\(id\)\)/g, linha.id);
                        res = res.replace(/\(\(equipamento\)\)/g,linha.descricao);
                        final += res;
                    }
                    final = '<option value="0">Selecione o equipamento</option>' + final;
                    $('select[name=equipamento]').html(final);
                }
            });
            $.getJSON('api/tecnicos/tipo/T', function(data) {
                if (data.length >= 0) {
                    var final = '';
                    modeloLinhaDropDownTecnico = modeloLinhaDropDownTecnico || $('select[name=tecnico]').html();
                    for (var i = 0; i < data.length; i++) {
                        var res = modeloLinhaDropDownTecnico;
                        var linha = data[i];
                        res = res.replace(/\(\(id\)\)/g, linha.id);
                        res = res.replace(/\(\(tecnico\)\)/g,linha.nome);
                        final += res;
                    }
                    final = '<option value="0">Selecione o técnico</option>' + final;
                    $('select[name=tecnico]').html(final);
                }
            });
            $.getJSON('api/tecnicos/tipo/G', function(data) {
                if (data.length >= 0) {
                    var final = '';
                    modeloLinhaDropDownGerente = modeloLinhaDropDownGerente || $('select[name=gerente]').html();
                    for (var i = 0; i < data.length; i++) {
                        var res = modeloLinhaDropDownGerente;
                        var linha = data[i];
                        res = res.replace(/\(\(id\)\)/g, linha.id);
                        res = res.replace(/\(\(gerente\)\)/g,linha.nome);
                        final += res;
                    }
                    final = '<option value="0">Selecione o gerente</option>' + final;
                    $('select[name=gerente]').html(final);
                }
            });
        }          

        function _inserir() {
            $('#cadastroChamado-modal').modal('hide') 
            $('#cadastroChamado-modal').modal('show') 
            _carregaDropDown()
            var titulo = 'Cadastro de Chamados <samp id="idModalLabelCadastro">((id))</samp>';
            $('.form-group').removeClass('has-error');
            $('#modalLabelCadastro').html(titulo);
            $('#idModalLabelCadastro').addClass('hide');
            _preencheForm(new Chamado());
            var data = new Date(Date.now()).toISOString().slice(0,16);
            $('input[name=emissao]').val(data);
            $('select[name=tipo]').val('I');
            $('select[name=status]').val('A');
            $('input[name=aprovacao]').val('');
        }

        function _preencheForm(registro) {
            if (registro.id != null) {
                var id = $('#idModalLabelCadastro').html();
                id = id.replace(/\(id\)/g, registro.id);
                $('#idModalLabelCadastro').html(id);            
                $('select[name=tipo]').val(registro.tipo);
                $('select[name=status]').val(registro.status);
                $('input[name=emissao]').val(registro.emissao);
                $('input[name=aprovacao]').val(registro.aprovacao);
                $('select[name=cliente]').val(registro.cliente.id);
                $('select[name=tecnico]').val(registro.tecnico.id);
                $('select[name=gerente]').val(registro.gerente.id);
                $('select[name=equipamento]').val(registro.equipamento.id);
            }    
            $('input[name=id]').val(registro.id);
            $('textarea[name=descricaoProblema]').val(registro.descricaoProblema);
            $('textarea[name=descricaoSolucao]').val(registro.descricaoSolucao);
        }

        function _salvar() {
            if (_validarCampos()) {
                var dados = $('form[role=cadastro]').serializeArray();
                var chamado = {};
                $.each(dados,
                    function(i, v) {
                        chamado[v.name] = v.value;
                    });
                chamado.cliente = {"id" : chamado.cliente}
                chamado.equipamento = {"id" : chamado.equipamento}
                chamado.tecnico = {"id" : chamado.tecnico}
                chamado.gerente = {"id" : chamado.gerente}
                chamado.emissao = $('input[name=emissao]').val();
                chamado.aprovacao = null;
                if ($('input[name=aprovacao]').val().length > 0) {
                    chamado.aprovacao = $('input[name=aprovacao]').val();
                }
                
                chamado.status = $('select[name=status]').val();                                    
                var cha = JSON.stringify(chamado);
                if (!chamado.id) {
                    $.ajax({
                            url: "api/chamados",
                            type: "POST",
                            contentType: 'application/json',
                            data: cha
                    }).done(function () {
                        _carregar();
                        showMessage('success', 'Chamado salvo com sucesso!');
                    }).error(function (data) {
                        showMessage('danger', data.status + ' - ' + data.responseText);
                    });
                } else {
                    $.ajax({
                            url: "api/chamados/" + chamado.id,
                            type: "PUT",
                            contentType: 'application/json',
                            data: cha
                                }).done(function () {
                                    _carregar();
                                }).error(function (data) {
                                    showMessage('danger', data.status + ' - ' + data.responseText);
                                });                    
                }
                $("#cadastroChamado-modal").modal("hide");
            }
        }

        function _renderTable(repositorio) {
            var final = '';
            modeloLinhaTabela = modeloLinhaTabela || $('table.table tbody').html();
            if (repositorio.length == 0) {
                $('table.table tbody').html('<tr><td>Nenhum Chamado cadastrado.</td></tr>');
            }
            else {
                for (var i = 0; i < repositorio.length; i++) {
                    var res = modeloLinhaTabela;
                    var linha = repositorio[i];
                    res = res.replace(/\(\(id\)\)/g, linha.id);
                    res = res.replace(/\(\(problema\)\)/g, linha.descricaoProblema);
                    res = res.replace(/\(\(emissao\)\)/g, formatDate(linha.emissao).substr(0, 10));
                    res = res.replace(/\(\(aprovacao\)\)/g, formatDate(linha.aprovacao).substr(0, 10));
                    if (linha.status == 'A') {
                        res = res.replace(/\(\(status\)\)/g, 'Aberto');
                    }
                    else if (linha.status == 'P') {
                        res = res.replace(/\(\(status\)\)/g, 'Aprovado');
                    }   
                    else if (linha.status == 'C') {
                        res = res.replace(/\(\(status\)\)/g, 'Cancelado');
                    }   
                    else if (linha.status == 'F') {
                        res = res.replace(/\(\(status\)\)/g, 'Finalizado');
                    }   
                    final += res;
                }
                $('table.table tbody').html(final);
            }
            $('#anterior').prop('disabled', pagina == 0);
        }
		
		function _setRemove(id) {
			idRemover = id;
		}

        function _remove() {
            $.getJSON('api/chamados/'+idRemover, function(data) {
                if (data.id > 0) {
                    $.ajax({
                        url: 'api/chamados/'+ data.id,
                        type: "DELETE",
                        contentType: 'application/json',
                        data: JSON.stringify(data)
                    }).done(function () {
                        _carregar();
                    }).error(function (data) {
                        showMessage('danger', data.status + ' - ' + data.responseText);
                    });
                }
            });            
            idRemover = 0;
        }

        function _editar(id) {
            $('#idModalLabelCadastro').removeClass();
            var titulo = 'Editar Chamado <samp id="idModalLabelCadastro">((id))</samp>';
            $('#modalLabelCadastro').html(titulo);
            $.getJSON('api/chamados/' + id, function (data) {
                _preencheForm(data);
            })
        }

        function _aprovar(id) {
            $('#idModalLabelCadastro').removeClass();
            var titulo = 'Aprovar Chamado <samp id="idModalLabelCadastro">((id))</samp>';
            $('#modalLabelCadastro').html(titulo);

            $.getJSON('api/chamados/' + id, function (data) {
                data.aprovacao = new Date(Date.now()).toISOString().slice(0,16);
                data.status = 'P';
                _preencheForm(data);
            });
        }

        function _cancelar(id) {
            $.getJSON('api/chamados/' + id, function (chamado) {
                chamado.status = 'C';
                $.ajax({
                        url: "api/chamados/"+ chamado.id,
                        type: "PUT",
                        contentType: 'application/json',
                        data: JSON.stringify(chamado)
                }).done(function () {
                    _carregar();
                    showMessage('success', 'Chamado cancelado com sucesso!');
                }).error(function (data) {
                    showMessage('danger', data.status + ' - ' + data.responseText);
                });               
            });
        }        

        function _finalizar(id) {
            $.getJSON('api/chamados/' + id, function (chamado) {
                if (chamado.status == 'P') {
                    chamado.status = 'F';
                    chamado.cliente = {"id" : chamado.cliente.id}
                    chamado.tecnico = {"id" : chamado.tecnico.id}
                    chamado.gerente = {"id" : chamado.gerente.id}
                    chamado.equipamento = {"id" : chamado.equipamento.id}
                    $.ajax({
                            url: "api/chamados/" + chamado.id,
                            type: "PUT",
                            contentType: 'application/json',
                            data: JSON.stringify(chamado)
                    }).done(function () {
                        _carregar();
                        showMessage('success', 'Chamado finalizado com sucesso!');
                    }).error(function (data) {
                        showMessage('danger', data.status + ' - ' + data.responseText);
                    });               
                }
                else if (chamado.status == 'F') {
                    showMessage('danger', 'Chamado já finalizado');
                }
                else {
                    showMessage('danger', 'Chamado deve estar aprovado para ser finalizado');    
                }
            });
        }        
		
		function _anterior() {
			if (pagina > 0) {
				pagina--;
				_carregar();
			}
		}
		
		function _proximo() {
			pagina++;
            _carregar();
		}

        function _validarCampos() {
            var retorno = true;
            $('#formChamado textarea#descricaoProblema').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado textarea#descricaoProblema').val()) {
                $('#formChamado textarea#descricaoProblema').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=cliente]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=cliente]').val() || $('#formChamado select[name=cliente]').val() == '0') {
                $('#formChamado select[name=cliente]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=equipamento]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=equipamento]').val() || $('#formChamado select[name=cliente]').val() == '0') {
                $('#formChamado select[name=equipamento]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=tecnico]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=tecnico]').val() || $('#formChamado select[name=cliente]').val() == '0') {
                $('#formChamado select[name=tecnico]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=gerente]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=gerente]').val() || $('#formChamado select[name=cliente]').val() == '0') {
                $('#formChamado select[name=gerente]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=tipo]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=tipo]').val()) {
                $('#formChamado select[name=tipo]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            $('#formChamado select[name=status]').closest('.form-group').removeClass('has-error');
            if (!$('#formChamado select[name=status]').val()) {
                $('#formChamado select[name=status]').closest('.form-group').addClass('has-error');
                retorno =  false;
            }
            return retorno;
        }
        
        return {
            inserir: _inserir,
            editar: _editar,
            aprovar: _aprovar,
            salvar: _salvar,
            setRemove: _setRemove,
            remove: _remove,
            anterior: _anterior,
            proximo: _proximo,
            pesquisar: _pesquisar,
            cancelar: _cancelar,
            finalizar: _finalizar,
            carregaDDW: _carregaDropDown()
        };
    }

    $(function(){        
        window.ctrl = chamadoControler();
        $('#btnSalvar').click(function(){
            ctrl.salvar();
        });
        $('#btnNovo').click(function(){
            ctrl.inserir();
        });
        $('#btnPesquisar').click(function(){
            ctrl.pesquisar();
        });
        ctrl.carregaDDW;
    });
})();