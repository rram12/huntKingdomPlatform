<?php

namespace ProductBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class ProductController extends Controller
{
    public function readAction()
    {
        return $this->render('ProductBundle:Product:read.html.twig', array(
            // ...
        ));
    }

    public function createAction()
    {
        return $this->render('ProductBundle:Product:create.html.twig', array(
            // ...
        ));
    }

    public function deleteAction()
    {
        return $this->render('ProductBundle:Product:delete.html.twig', array(
            // ...
        ));
    }

    public function updateAction()
    {
        return $this->render('ProductBundle:Product:update.html.twig', array(
            // ...
        ));
    }

}
