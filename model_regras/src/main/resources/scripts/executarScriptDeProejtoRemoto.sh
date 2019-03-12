#!/bin/bash
echo "Executando :scriptProjeto em servidor "
ssh git@coletivojava.com.br -p 667 'bash -s' < :scriptProjeto :projeto