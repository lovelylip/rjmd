import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dm-cqbh.reducer';
import { IDmCqbh } from 'app/shared/model/dm-cqbh.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDmCqbhDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DmCqbhDetail = (props: IDmCqbhDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dmCqbhEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="rjmdApp.dmCqbh.detail.title">DmCqbh</Translate> [<b>{dmCqbhEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="ma">
              <Translate contentKey="rjmdApp.dmCqbh.ma">Ma</Translate>
            </span>
          </dt>
          <dd>{dmCqbhEntity.ma}</dd>
          <dt>
            <span id="ten">
              <Translate contentKey="rjmdApp.dmCqbh.ten">Ten</Translate>
            </span>
          </dt>
          <dd>{dmCqbhEntity.ten}</dd>
        </dl>
        <Button tag={Link} to="/dm-cqbh" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dm-cqbh/${dmCqbhEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ dmCqbh }: IRootState) => ({
  dmCqbhEntity: dmCqbh.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DmCqbhDetail);
