package br.com.siswbrasil.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@SuppressWarnings("serial")
@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {


    @Inject
    EntityManager manager;

    @AroundInvoke
    public Object executaTX(InvocationContext contexto) throws Exception {

        System.out.println("abrindo tx");
        manager.getTransaction().begin();

        //chamar os daos que precisam de um TX
        Object resultado = contexto.proceed();

        System.out.println("fechando tx");
        manager.getTransaction().commit();

         return resultado;
    }
}