<?php

namespace ProductBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class PanierController extends Controller
{
    public function readAction()
    {
        return $this->render('ProductBundle:Panier:read.html.twig', array(
            // ...
        ));
    }

    public function createAction()
    {
        return $this->render('ProductBundle:Panier:create.html.twig', array(
            // ...
        ));
    }

    public function deleteAction()
    {
        return $this->render('ProductBundle:Panier:delete.html.twig', array(
            // ...
        ));
    }

    public function updateAction()
    {
        return $this->render('ProductBundle:Panier:update.html.twig', array(
            // ...
        ));
    }

}
