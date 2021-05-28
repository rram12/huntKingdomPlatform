<?php

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
class DefaultController extends Controller
{
    /**
     * @Route("/", name="homepage")
     */
    public function indexAction(Request $request)
    {
        // replace this example code with whatever you need
        return $this->render('default/index.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')).DIRECTORY_SEPARATOR,
        ]);
    }

     /**
     * Require ROLE_ADMIN for only this controller method.
     *
     * @IsGranted("ROLE_ADMIN")
     */
    public function dashboardAction()
    {


        return $this->render('dashboard/dashboard.html.twig');
    }


    public function frontAction()
    {
        return $this->render('front/index.html.twig');
    }

    public function aboutAction()
    {
        return $this->render('front/about.html.twig');
    }

    public function please_waitAction()
    {
        return $this->render('front/please_wait.html.twig');
    }
}
