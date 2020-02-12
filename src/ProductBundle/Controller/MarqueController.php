<?php

namespace ProductBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class MarqueController extends Controller
{
    public function readAction()
    {
        return $this->render('ProductBundle:Marque:read.html.twig', array(
            // ...
        ));
    }

    public function createAction()
    {
        return $this->render('ProductBundle:Marque:create.html.twig', array(
            // ...
        ));
    }

    public function updateAction()
    {
        return $this->render('ProductBundle:Marque:update.html.twig', array(
            // ...
        ));
    }

    public function deleteAction()
    {
        return $this->render('ProductBundle:Marque:delete.html.twig', array(
            // ...
        ));
    }

}
