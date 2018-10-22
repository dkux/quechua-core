import './home.css';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';

export interface IHomeProp extends StateProps, DispatchProps {}

export class Home extends React.Component<IHomeProp> {
  componentDidMount() {
    this.props.getSession();
  }

  render() {
    const { account } = this.props;
    return (
      <Row>
        <Col md="9">
          <h2>Bienvenido a Quechua</h2>
          {account && account.login ? (
            <div>
              <Alert color="success">Se encuentra logueado como {account.login}.</Alert>
            </div>
          ) : (
            <div>
              <Alert color="warning">
                Desea
                <Link to="/login" className="alert-link">
                  {' '}
                  Loguearse
                </Link>
              </Alert>
            </div>
          )}
        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

const mapDispatchToProps = { getSession };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
